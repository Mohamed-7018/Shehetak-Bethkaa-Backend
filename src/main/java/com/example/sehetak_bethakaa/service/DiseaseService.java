package com.example.sehetak_bethakaa.service;

import com.example.sehetak_bethakaa.dto.request.CreateDiseaseRequest;
import com.example.sehetak_bethakaa.dto.response.DiseaseResponse;
import com.example.sehetak_bethakaa.entity.Disease;
import com.example.sehetak_bethakaa.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public DiseaseResponse createDisease(CreateDiseaseRequest request) {

        Disease disease = Disease.builder()
                .name(request.getName())
                .gender(request.getGender())
                .type(request.getType())
                .description(request.getDescription())
                .build();

        diseaseRepository.save(disease);

        return DiseaseResponse.builder()
                .id(disease.getId())
                .name(disease.getName())
                .gender(disease.getGender())
                .type(disease.getType())
                .description(disease.getDescription())
                .build();
    }

    public List<DiseaseResponse> getAllDiseases() {
        return diseaseRepository.findAll().stream()
                .map(disease -> DiseaseResponse.builder()
                        .id(disease.getId())
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public DiseaseResponse getDiseaseById(Long id) {
        return diseaseRepository.findById(id)
                .map(disease -> DiseaseResponse.builder()
                        .id(disease.getId())
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .orElse(null);
    }

    public DiseaseResponse updateDisease(Long id, CreateDiseaseRequest request) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disease not found"));

        disease.setName(request.getName());
        disease.setGender(request.getGender());
        disease.setType(request.getType());
        disease.setDescription(request.getDescription());

        diseaseRepository.save(disease);

        return DiseaseResponse.builder()
                .id(disease.getId())
                .name(disease.getName())
                .gender(disease.getGender())
                .type(disease.getType())
                .description(disease.getDescription())
                .build();
    }

    public void deleteDisease(Long id) {
        diseaseRepository.deleteById(id);
    }

    public List<DiseaseResponse> searchDiseases(String name) {
        return diseaseRepository.findByNameContainingIgnoreCase(name).stream()
                .map(disease -> DiseaseResponse.builder()
                        .id(disease.getId())
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DiseaseResponse> searchDiseasesByGender(String gender) {

        List<String> genders = new ArrayList<>();
        genders.add("BOTH");
        genders.add(gender.toUpperCase());

        return diseaseRepository.findByGenderIn(genders).stream()
                .map(disease -> DiseaseResponse.builder()
                        .id(disease.getId())
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DiseaseResponse> searchDiseasesByType(String type) {
        List<String> types = new ArrayList<>();
        types.add("BOTH");
        types.add(type.toUpperCase());

        return diseaseRepository.findByTypeIn(types).stream()
                .map(disease -> DiseaseResponse.builder()
                        .id(disease.getId())
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DiseaseResponse> searchDiseasesByTypeAndGender(String type, String gender) {

        List<String> genders = new ArrayList<>();
        genders.add("BOTH");
        genders.add(gender.toUpperCase());

        List<String> types = new ArrayList<>();
        types.add("BOTH");
        types.add(type.toUpperCase());

        return diseaseRepository.findByTypeAndGenderIn(types, genders).stream()
                .map(disease -> DiseaseResponse.builder()
                        .id(disease.getId())
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DiseaseResponse> addListDiseases(List<CreateDiseaseRequest> diseases) {
        List<Disease> diseaseList = diseases.stream()
                .map(disease -> Disease.builder()
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .collect(Collectors.toList());

        diseaseRepository.saveAll(diseaseList);

        return diseaseList.stream()
                .map(disease -> DiseaseResponse.builder()
                        .id(disease.getId())
                        .name(disease.getName())
                        .gender(disease.getGender())
                        .type(disease.getType())
                        .description(disease.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}