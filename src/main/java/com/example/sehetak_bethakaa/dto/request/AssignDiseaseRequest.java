package com.example.sehetak_bethakaa.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignDiseaseRequest {
    @NotNull
    private Long diseaseId;

    /**
     * true => assign to historyDiseases
     * false => assign to diseases (current)
     */
    private boolean history = false;
}