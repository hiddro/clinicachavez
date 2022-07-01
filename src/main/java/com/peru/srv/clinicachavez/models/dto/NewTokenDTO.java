package com.peru.srv.clinicachavez.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewTokenDTO {

    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "newToken")
    private String newToken;
}
