package com.peru.srv.clinicachavez.service;

import com.peru.srv.clinicachavez.models.dto.NewTokenDTO;
import com.peru.srv.clinicachavez.models.dto.TokenPropertiesDTO;
import com.peru.srv.clinicachavez.models.entities.TokenProperties;

import java.util.*;

public interface ITokenService {

    TokenProperties saveToken(TokenPropertiesDTO tokenDto);

    TokenProperties updateToken(NewTokenDTO newTokenDto);

    List<TokenProperties> getAllToken();
}
