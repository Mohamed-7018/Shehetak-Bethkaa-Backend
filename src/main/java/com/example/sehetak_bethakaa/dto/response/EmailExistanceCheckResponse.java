package com.example.sehetak_bethakaa.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailExistanceCheckResponse {

    @JsonProperty("email")
    String email;

    @JsonProperty("is_email_exists")
    boolean emailExists;

}
