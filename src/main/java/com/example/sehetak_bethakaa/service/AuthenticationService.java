package com.example.sehetak_bethakaa.service;

import com.example.sehetak_bethakaa.config.JwtService;
import com.example.sehetak_bethakaa.dto.request.ForgetPasswordRequest;
import com.example.sehetak_bethakaa.dto.request.LoginRequest;
import com.example.sehetak_bethakaa.dto.request.RegisterRequest;
import com.example.sehetak_bethakaa.dto.response.AuthenticationResponse;
import com.example.sehetak_bethakaa.dto.response.EmailExistanceCheckResponse;
import com.example.sehetak_bethakaa.entity.Disease;
import com.example.sehetak_bethakaa.entity.NotificationEmail;
import com.example.sehetak_bethakaa.entity.Role;
import com.example.sehetak_bethakaa.entity.User;
import com.example.sehetak_bethakaa.entity.UserHealthProfile;
import com.example.sehetak_bethakaa.entity.VerificationToken;
import com.example.sehetak_bethakaa.exception.IllegalOperationException;
import com.example.sehetak_bethakaa.exception.ResourceNotFoundException;
import com.example.sehetak_bethakaa.repository.DiseaseRepository;
import com.example.sehetak_bethakaa.repository.UserHealthProfileRepository;
import com.example.sehetak_bethakaa.repository.UserRepository;
import com.example.sehetak_bethakaa.repository.VerificationTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserHealthProfileRepository userHealthProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final EmailBuilderService emailBuilderService;
    private final DiseaseRepository diseaseRepository;

    public EmailExistanceCheckResponse checkEmailExists(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return EmailExistanceCheckResponse.builder().email(email).emailExists(!userOptional.isEmpty()).build();
    }

    public void register(RegisterRequest request) throws IOException {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf("USER"))
                .enabled(false)
                .build();

        List<Disease> diseases = diseaseRepository.findByIdIn(List.of(1L, 2L));
        List<Disease> historyDiseases = diseaseRepository.findByIdIn(List.of(3L, 4L));

        var userProfile = UserHealthProfile.builder()
                .user(user)
                .phoneNumber(request.getPhoneNumber())
                .name(request.getUserName())
                .birthday(request.getBirthday())
                .height(request.getHeight())
                .weight(request.getWeight())
                .bloodType(request.getBloodType())
                .doUserSmoke(request.isDoUserSmoke())
                .notes(request.getNotes())
                .diseases(diseases)
                .historyDiseases(historyDiseases)
                .build();

        userRepository.save(user);
        userHealthProfileRepository.save(userProfile);

        String token = generateVerificationToken(user);

        String html = emailBuilderService.buildVerificationEmail(token);

        mailService.sendHtmlMail(
                new NotificationEmail(
                        "Sehetak Bethakaa - Activate your account",
                        user.getEmail(),
                        html));
    }

    public void resendVerificationEmail(String email) throws IOException {
        // 1. Find the user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        // 2. Only if user is not yet enabled
        if (user.isEnabled()) {
            throw new IllegalOperationException("Account is already verified");
        }

        // 3. Revoke old tokens
        revokeAllVerificationToken(user);

        // 4. Generate new token
        String token = generateVerificationToken(user);

        // 5. Build the email
        String html = emailBuilderService.buildVerificationEmail(token);

        // 6. Send email
        mailService.sendHtmlMail(
                new NotificationEmail(
                        "Sehetak Bethakaa - Activate your account",
                        user.getEmail(),
                        html));
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .uuid(user.getId())
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .uuid(user.getId())
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public String generateVerificationToken(User user) {
        if (user.isEnabled())
            revokeAllVerificationToken(user);

        String token = UUID.randomUUID().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 4);
        Date expirationTime = calendar.getTime();

        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expirationTime(expirationTime)
                .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private void revokeAllVerificationToken(User user) {
        var verificationTokens = verificationTokenRepository.findByUser(user);
        if (verificationTokens.isEmpty())
            return;
        verificationTokenRepository.deleteAll(verificationTokens);
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(
                verificationToken.orElseThrow(() -> new IllegalOperationException("Invalid Verification Token")));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", "email"));
        user.setEnabled(true);
        revokeAllVerificationToken(user);
        userRepository.save(user);
    }

    public void forgetMyPaswToken(String email) throws IOException {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        String token = generateVerificationToken(existingUser);

        // 5. Build the email
        String html = emailBuilderService.buildForgetPasswordEmail(token);

        // 6. Send email
        mailService.sendHtmlMail(
                new NotificationEmail(
                        "Sehetak Bethakaa - Forget Password",
                        existingUser.getEmail(),
                        html));
        // mailService.sendMail(new NotificationEmail("Forget My Password",
        // existingUser.getEmail(), "Please copy this token = " + token));
    }

    public void forgetChangePasw(ForgetPasswordRequest forgetPasswordRequest) {

        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(forgetPasswordRequest.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("VerificationToken", "token",
                        forgetPasswordRequest.getToken()));

        Date now = new Date();

        if (verificationToken.getExpirationTime().after(now)) {
            User user = verificationToken.getUser();
            user.setPassword(passwordEncoder.encode(forgetPasswordRequest.getNewPasw()));
            userRepository.save(user);
        } else {
            throw new IllegalOperationException("VerificationToken is expired");
        }
    }

    public User getCurrentUser() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email",
                        SecurityContextHolder.getContext().getAuthentication().getName()));
        return user;
    }

    public AuthenticationResponse signInAnonymously() {
        // short id for username/email
        String id = UUID.randomUUID().toString();
        String shortId = id.substring(0, 8);

        User guest = User.builder()
                .firstName("Guest")
                .lastName("") // optional
                .userName("guest_" + shortId)
                .email("guest_" + id + "@anonymous.local")
                // store a random encoded password just in case (not used to login)
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .role(Role.GUEST) // add this enum value
                .enabled(true) // enable so jwt checks pass
                .build();

        List<Disease> diseases = diseaseRepository.findByIdIn(List.of(1L, 2L));
        List<Disease> historyDiseases = diseaseRepository.findByIdIn(List.of(3L, 4L));

        var userProfile = UserHealthProfile.builder()
                .user(guest)
                .phoneNumber("+2 01006074955")
                .name("guest_\" + shortId")
                .birthday(LocalDate.of(2000, 1, 1))
                .height(1.75)
                .weight(70.9)
                .bloodType("A+")
                .doUserSmoke(false)
                .notes("notes")
                .diseases(diseases)
                .historyDiseases(historyDiseases)
                .build();

        userRepository.save(guest);
        userHealthProfileRepository.save(userProfile);

        String jwtToken = jwtService.generateToken(guest);
        String refreshToken = jwtService.generateRefreshToken(guest);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .uuid(guest.getId())
                .build();
    }

    public void logout(HttpServletRequest request) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(token);

        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

            // Clear security context
            SecurityContextHolder.clearContext();

            log.info("User {} logged out successfully", userEmail);
        }
    }
}
