package com.example.sehetak_bethakaa.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailBuilderService {

    public String buildVerificationEmail(String token) throws IOException {
        String verificationLink = "http://localhost:8082/api/auth/accountVerification/" + token;

        // Load HTML template from resources
        InputStream resource = getClass().getResourceAsStream("/templates/verify-email.html");
        String htmlTemplate = StreamUtils.copyToString(resource, StandardCharsets.UTF_8);

        // Replace placeholder with actual link
        return htmlTemplate.replace("{{VERIFICATION_LINK}}", verificationLink);
    }

    public String buildForgetPasswordEmail(String token) throws IOException {
        String resetLink = "http://localhost:8082/api/auth/resetPassword/" + token;

        // Load HTML template from resources
        InputStream resource = getClass().getResourceAsStream("/templates/forget-pass.html");
        String htmlTemplate = StreamUtils.copyToString(resource, StandardCharsets.UTF_8);

        // Replace placeholders with actual values
        return htmlTemplate.replace("{{RESET_PASSWORD_LINK}}", resetLink)
                .replace("{{RESET_TOKEN}}", token);
    }
}