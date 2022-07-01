package com.peru.srv.clinicachavez.service.impl;

import com.peru.srv.clinicachavez.models.dto.NewTokenDTO;
import com.peru.srv.clinicachavez.models.dto.TokenPropertiesDTO;
import com.peru.srv.clinicachavez.models.entities.TokenProperties;
import com.peru.srv.clinicachavez.repositories.TokenRepository;
import com.peru.srv.clinicachavez.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.peru.srv.clinicachavez.utils.Constant.*;

@Service
@Slf4j
@Transactional
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TokenProperties saveToken(TokenPropertiesDTO tokenDto) {
        log.info("Creando Token para la BD!");
        Optional<TokenProperties> tokenRepo = tokenRepository.findByToken(tokenDto.getToken());

        if (tokenRepo.isPresent()) {
            log.info("Token ya existe");
            throw new RuntimeException("Token " + tokenDto.getToken() + " Existe!");
        }

        TokenProperties tokenConvert = modelMapper.map(tokenDto, TokenProperties.class);
        tokenConvert.setEstado(ACTIVO);

        return tokenRepository.save(tokenConvert);
    }

    @Override
    public TokenProperties updateToken(NewTokenDTO newTokenDTO) {
        log.info("Actualizando Token para la BD!");
        Optional<TokenProperties> tokenRepo = tokenRepository.findByToken(newTokenDTO.getToken());

        if (!tokenRepo.isPresent()) {
            log.info("Token no existe");
            throw new RuntimeException("Token " + newTokenDTO.getToken() + " No Existe!");
        }

        tokenRepo.get().setToken(newTokenDTO.getNewToken());

        return tokenRepository.save(tokenRepo.get());
    }

    @Override
    public List<TokenProperties> getAllToken() {
        log.info("Obteniendo todos los Tokens!");
        return tokenRepository.findAll();
    }
}
