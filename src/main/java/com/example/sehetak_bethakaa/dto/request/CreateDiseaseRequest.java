package com.example.sehetak_bethakaa.dto.request;

import lombok.Data;

@Data
public class CreateDiseaseRequest {

    private String name;

    private String gender; // MALE, FEMALE, BOTH

    private String type; // PERSONAL or FAMILY

    private String description;
}