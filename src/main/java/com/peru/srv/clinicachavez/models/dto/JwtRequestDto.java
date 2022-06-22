package com.peru.srv.clinicachavez.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestDto implements Serializable {

    private static final long serialVersionUID = 6126350218322901197L;

    private String username;

    private String password;
}
