
package com.example.sehetak_bethakaa.service;

import com.example.sehetak_bethakaa.dto.request.AssignDiseaseRequest;
import com.example.sehetak_bethakaa.dto.request.UserHealthProfileRequest;
import com.example.sehetak_bethakaa.dto.response.DiseaseResponse;
import com.example.sehetak_bethakaa.dto.response.UserHealthProfileResponse;
import com.example.sehetak_bethakaa.entity.Disease;
import com.example.sehetak_bethakaa.entity.User;
import com.example.sehetak_bethakaa.entity.UserHealthProfile;
import com.example.sehetak_bethakaa.exception.ResourceNotFoundException;
import com.example.sehetak_bethakaa.repository.DiseaseRepository;
import com.example.sehetak_bethakaa.repository.UserHealthProfileRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserHealthProfileService {

    private final UserHealthProfileRepository profileRepository;
    private final DiseaseRepository diseaseRepository;
    private final AuthenticationService authenticationService; // for getCurrentUser()

    public UserHealthProfileResponse getCurrentUserProfile() {
        User user = authenticationService.getCurrentUser();
        return profileRepository.findByUserId(user.getId())
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("UserHealthProfile", "userId", user.getId().toString()));
    }

    @Transactional
    public UserHealthProfileResponse createOrUpdate(UserHealthProfileRequest request) {
        User user = authenticationService.getCurrentUser();

        UserHealthProfile profile = profileRepository.findByUserId(user.getId())
                .orElse(UserHealthProfile.builder().user(user).build());

        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setName(request.getName());
        profile.setBirthday(request.getBirthday());
        profile.setHeight(request.getHeight());
        profile.setWeight(request.getWeight());
        profile.setBloodType(request.getBloodType());
        profile.setDoUserSmoke(request.isDoUserSmoke());

        // compute BMI if possible
        if (request.getHeight() != null && request.getWeight() != null
                && request.getHeight() > 0) {
            double bmi = request.getWeight() / (request.getHeight() * request.getHeight());
            profile.setBmi(Math.round(bmi * 100.0) / 100.0); // round to 2 decimals
        } else {
            profile.setBmi(null);
        }

        // set/replace diseases lists if provided
        if (request.getDiseaseIds() != null) {
            List<Disease> diseases = fetchDiseasesByIds(request.getDiseaseIds());
            profile.setDiseases(diseases);
        }

        if (request.getHistoryDiseaseIds() != null) {
            List<Disease> history = fetchDiseasesByIds(request.getHistoryDiseaseIds());
            profile.setHistoryDiseases(history);
        }

        profile = profileRepository.save(profile);

        return toResponse(profile);
    }

    @Transactional
    public UserHealthProfileResponse assignDisease(AssignDiseaseRequest req) {
        User user = authenticationService.getCurrentUser();
        UserHealthProfile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserHealthProfile", "userId", user.getId().toString()));

        Disease disease = diseaseRepository.findById(req.getDiseaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Disease", "id", req.getDiseaseId().toString()));

        if (req.isHistory()) {
            if (profile.getHistoryDiseases() == null) profile.setHistoryDiseases(new ArrayList<>());
            if (profile.getHistoryDiseases().stream().noneMatch(d -> d.getId().equals(disease.getId()))) {
                profile.getHistoryDiseases().add(disease);
            }
        } else {
            if (profile.getDiseases() == null) profile.setDiseases(new ArrayList<>());
            if (profile.getDiseases().stream().noneMatch(d -> d.getId().equals(disease.getId()))) {
                profile.getDiseases().add(disease);
            }
        }

        profile = profileRepository.save(profile);
        return toResponse(profile);
    }

    @Transactional
    public UserHealthProfileResponse removeDisease(Long diseaseId, boolean history) {
        User user = authenticationService.getCurrentUser();
        UserHealthProfile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserHealthProfile", "userId", user.getId().toString()));

        if (history) {
            if (profile.getHistoryDiseases() != null) {
                profile.setHistoryDiseases(profile.getHistoryDiseases().stream()
                        .filter(d -> !d.getId().equals(diseaseId))
                        .collect(Collectors.toList()));
            }
        } else {
            if (profile.getDiseases() != null) {
                profile.setDiseases(profile.getDiseases().stream()
                        .filter(d -> !d.getId().equals(diseaseId))
                        .collect(Collectors.toList()));
            }
        }

        profile = profileRepository.save(profile);
        return toResponse(profile);
    }

    // --- helpers ---
    public List<Disease> fetchDiseasesByIds(List<Long> ids) {
        if (ids.isEmpty()) return Collections.emptyList();
        List<Disease> diseases = diseaseRepository.findAllById(ids);
        // ensure all requested ids were found
        Set<Long> found = diseases.stream().map(Disease::getId).collect(Collectors.toSet());
        List<Long> missing = ids.stream().filter(id -> !found.contains(id)).collect(Collectors.toList());
        if (!missing.isEmpty()) {
            throw new ResourceNotFoundException("Disease", "ids", missing.toString());
        }
        return diseases;
    }

    private UserHealthProfileResponse toResponse(UserHealthProfile profile) {
        List<DiseaseResponse> diseases = Optional.ofNullable(profile.getDiseases())
                .orElse(Collections.emptyList())
                .stream()
                .map(d -> DiseaseResponse.builder()
                        .id(d.getId())
                        .name(d.getName())
                        .gender(d.getGender())
                        .type(d.getType())
                        .description(d.getDescription())
                        .build())
                .collect(Collectors.toList());

        List<DiseaseResponse> history = Optional.ofNullable(profile.getHistoryDiseases())
                .orElse(Collections.emptyList())
                .stream()
                .map(d -> DiseaseResponse.builder()
                        .id(d.getId())
                        .name(d.getName())
                        .gender(d.getGender())
                        .type(d.getType())
                        .description(d.getDescription())
                        .build())
                .collect(Collectors.toList());

        return UserHealthProfileResponse.builder()
                .id(profile.getId())
                .phoneNumber(profile.getPhoneNumber())
                .name(profile.getName())
                .birthday(profile.getBirthday())
                .height(profile.getHeight())
                .weight(profile.getWeight())
                .bmi(profile.getBmi())
                .bloodType(profile.getBloodType())
                .doUserSmoke(profile.isDoUserSmoke())
                .diseases(diseases)
                .historyDiseases(history)
                .build();
    }
}