package com.example.sehetak_bethakaa.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    private static final String FIRST_NAME_ERROR = "firstName is required";
    private static final String LAST_NAME_ERROR = "lastName is required";
    private static final String USER_NAME_ERROR = "userName is required";
    private static final String EMAIL_ERROR = "email is required";
    private static final String PASSWORD_ERROR = "password is required";

    @NotBlank(message = FIRST_NAME_ERROR)
    private String firstName;
    @NotBlank(message = LAST_NAME_ERROR)
    private String lastName;
    @NotBlank(message = USER_NAME_ERROR)
    private String userName;

    @Email
    @NotBlank(message = EMAIL_ERROR)
    private String email;

    @NotBlank(message = PASSWORD_ERROR)
    private String password;

    @JsonProperty("phone_number")
    private String phoneNumber;



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
