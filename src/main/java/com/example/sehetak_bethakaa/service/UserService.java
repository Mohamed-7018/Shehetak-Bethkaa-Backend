package com.example.sehetak_bethakaa.service;

import com.example.sehetak_bethakaa.entity.User;
import com.example.sehetak_bethakaa.exception.IllegalOperationException;
import com.example.sehetak_bethakaa.repository.UserRepository;
import com.example.sehetak_bethakaa.dto.request.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service    
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(UpdatePasswordRequest request) {
        User user = authenticationService.getCurrentUser();
        if(passwordEncoder.matches(request.getOldPasw(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPasw()));
            userRepository.save(user);
        } else
            throw new IllegalOperationException("Password unmatched!");
    }
}
