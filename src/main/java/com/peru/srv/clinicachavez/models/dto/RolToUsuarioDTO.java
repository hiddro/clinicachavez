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
public class RolToUsuarioDTO {

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "titulo")
    private String titulo;
}
