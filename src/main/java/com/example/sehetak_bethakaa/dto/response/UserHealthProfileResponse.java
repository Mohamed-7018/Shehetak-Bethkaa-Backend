package com.example.sehetak_bethakaa.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
public class UserHealthProfileResponse {

    private Long id;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birthday")
    private LocalDate birthday;

    @JsonProperty("height")
    private Double height;

    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("bmi")
    private Double bmi;

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("do_user_smoke")
    private boolean doUserSmoke;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("diseases")
    private List<DiseaseResponse> diseases;

    @JsonProperty("history_diseases")
    private List<DiseaseResponse> historyDiseases;
}