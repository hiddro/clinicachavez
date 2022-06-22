package com.peru.srv.clinicachavez.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtResponseDto implements Serializable {

    private static final long serialVersionUID = -5509557811759306602L;

    private final String token;

}
