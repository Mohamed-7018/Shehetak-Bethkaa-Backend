package com.example.sehetak_bethakaa.controller;

import com.example.sehetak_bethakaa.annotation.AuthorizeAdmin;
import com.example.sehetak_bethakaa.dto.request.ForgetPasswordRequest;
import com.example.sehetak_bethakaa.dto.request.LoginRequest;
import com.example.sehetak_bethakaa.dto.request.RegisterRequest;
import com.example.sehetak_bethakaa.dto.response.AuthenticationResponse;
import com.example.sehetak_bethakaa.dto.response.EmailExistanceCheckResponse;
import com.example.sehetak_bethakaa.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/account_exists/{email}")
    @AuthorizeAdmin
    public EmailExistanceCheckResponse checkEmailExistance(@PathVariable String email) {
        return service.checkEmailExists(email);
    }

    @PostMapping("/register")
    @AuthorizeAdmin
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) throws IOException {
        service.register(request);
        return ResponseEntity.status(OK)
                .body("User Registiration Successfully");
    }

    @PostMapping("/resend-verification-email/{email}")
    @AuthorizeAdmin
    public ResponseEntity<String> resendVerificationEmail(
            @PathVariable String email) throws IOException {
        service.resendVerificationEmail(email);
        return ResponseEntity.ok("Verification email resent successfully");
    }

    @PostMapping("/anonymous")
    @AuthorizeAdmin
    public ResponseEntity<AuthenticationResponse> signInAnonymously() {
        return ResponseEntity.ok(service.signInAnonymously());
    }

    @PostMapping("/login")
    @AuthorizeAdmin
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh/token")
    @AuthorizeAdmin
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        service.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/forgetMyPassword/{email}")
    @AuthorizeAdmin
    public ResponseEntity<String> forgetMyPassword(@PathVariable String email) throws IOException {
        service.forgetMyPaswToken(email);
        return new ResponseEntity<>("Token is delivered", OK);
    }

    @PutMapping("/forgetMyPassword/newPasw")
    @AuthorizeAdmin
    public ResponseEntity<String> forgetChangePasword(
            @Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        service.forgetChangePasw(forgetPasswordRequest);
        return new ResponseEntity<>("New Password is determined", OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        service.logout(request);
        return ResponseEntity.ok("Logged out successfully");
    }
}