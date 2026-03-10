package com.example.sehetak_bethakaa.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiseaseResponse {
    private Long id;
    private String name;
    private String gender;
    private String type;
    private String description;
}