package com.example.sehetak_bethakaa.controller;

import com.example.sehetak_bethakaa.annotation.AuthorizeAdmin;
import com.example.sehetak_bethakaa.dto.request.CreateDiseaseRequest;
import com.example.sehetak_bethakaa.dto.response.DiseaseResponse;
import com.example.sehetak_bethakaa.service.DiseaseService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TG/diseases")
@RequiredArgsConstructor
public class DiseaseController {

    private final DiseaseService diseaseService;

    @PostMapping("/create_new_disease")
    @AuthorizeAdmin
    public DiseaseResponse createDisease(@RequestBody CreateDiseaseRequest request) {
        return diseaseService.createDisease(request);
    }

    @PostMapping("/create_new_diseases")
    @AuthorizeAdmin
    public List<DiseaseResponse> createDiseases(@RequestBody List<CreateDiseaseRequest> requests) {
        return diseaseService.addListDiseases(requests);
    }

    @DeleteMapping("/delete_disease/{id}")
    @AuthorizeAdmin
    public String deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return "Disease deleted successfully";
    }

    @GetMapping("/get_all_diseases")
    @AuthorizeAdmin
    public List<DiseaseResponse> getAllDiseases() {
        return diseaseService.getAllDiseases();
    }
    
    @GetMapping("/get_disease_by_id/{id}")
    @AuthorizeAdmin
    public DiseaseResponse getDiseaseById(@PathVariable Long id) {
        return diseaseService.getDiseaseById(id);
    }

    @PutMapping("/update_disease/{id}")
    @AuthorizeAdmin
    public DiseaseResponse updateDisease(@PathVariable Long id, @RequestBody CreateDiseaseRequest request) {
        return diseaseService.updateDisease(id, request);
    }

    @GetMapping("/search_diseases/{name}")
    @AuthorizeAdmin
    public List<DiseaseResponse> searchDiseases(@PathVariable String name) {
        return diseaseService.searchDiseases(name);
    }

    @GetMapping("/search_diseases_by_gender/{gender}")
    @AuthorizeAdmin
    public List<DiseaseResponse> searchDiseasesByGender(@PathVariable String gender) {
        return diseaseService.searchDiseasesByGender(gender);
    }

    @GetMapping("/search_diseases_by_type/{type}")
    @AuthorizeAdmin
    public List<DiseaseResponse> searchDiseasesByType(@PathVariable String type) {
        return diseaseService.searchDiseasesByType(type);
    }

    @GetMapping("/search_diseases_by_type_and_gender/{type}/{gender}")
    @AuthorizeAdmin
    public List<DiseaseResponse> searchDiseasesByTypeAndGender(@PathVariable String type, @PathVariable String gender) {
        return diseaseService.searchDiseasesByTypeAndGender(type, gender);
    }
}