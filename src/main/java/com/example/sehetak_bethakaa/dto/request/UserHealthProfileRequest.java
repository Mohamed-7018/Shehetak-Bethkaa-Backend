package com.example.sehetak_bethakaa.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class UserHealthProfileRequest {
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birthday")
    private LocalDate birthday;

    @DecimalMin(value = "0.3", message = "height must be a positive number in meters")
    @JsonProperty("height")
    private Double height; // meters

    @DecimalMin(value = "0.1", message = "weight must be a positive number in kg")
    @JsonProperty("weight")
    private Double weight; // kg

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("do_user_smoke")
    private boolean doUserSmoke;

    @JsonProperty("notes")
    private String notes;

    /**
     * List of disease IDs to be set as current diseases (optional).
     * If provided, it will replace the current list.
     */
    @JsonProperty("disease_ids")
    private List<Long> diseaseIds;

    /**
     * List of disease IDs to be set as history diseases (optional).
     * If provided, it will replace the history list.
     */
    @JsonProperty("history_disease_ids")
    private List<Long> historyDiseaseIds;
}