package com.example.sehetak_bethakaa.controller;

import com.example.sehetak_bethakaa.annotation.AuthorizeAdmin;
import com.example.sehetak_bethakaa.dto.request.UserHealthProfileRequest;
import com.example.sehetak_bethakaa.dto.response.UserHealthProfileResponse;
import com.example.sehetak_bethakaa.service.UserHealthProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TG/health-profile")
@RequiredArgsConstructor
public class UserHealthProfileController {

    private final UserHealthProfileService service;

    /**
     * Get the current authenticated user's health profile.
     */
    @GetMapping
    @AuthorizeAdmin
    @PreAuthorize("hasAuthority('tg:read')")
    public ResponseEntity<UserHealthProfileResponse> getProfile() {
        return ResponseEntity.ok(service.getCurrentUserProfile());
    }

    /**
     * Create or update the current user's profile.
     * If diseaseIds/historyDiseaseIds are provided they replace the corresponding lists.
     */
    @PostMapping
    @AuthorizeAdmin
    @PreAuthorize("hasAuthority('tg:update')")
    public ResponseEntity<UserHealthProfileResponse> createOrUpdate(
            @Valid @RequestBody UserHealthProfileRequest request) {
        return ResponseEntity.ok(service.createOrUpdate(request));
    }


}