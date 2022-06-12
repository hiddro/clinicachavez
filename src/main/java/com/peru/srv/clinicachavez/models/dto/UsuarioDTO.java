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
public class UsuarioDTO {

    @JsonProperty(value = "nombre")
    private String nombre;

    @JsonProperty(value = "userName")
    private String username;

    @JsonProperty(value = "password")
    private String password;
}
