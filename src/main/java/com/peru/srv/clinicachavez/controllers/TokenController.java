package com.peru.srv.clinicachavez.controllers;

import com.peru.srv.clinicachavez.Filter.TokenManager;
import com.peru.srv.clinicachavez.models.dto.JwtRequestDto;
import com.peru.srv.clinicachavez.models.dto.JwtResponseDto;
import com.peru.srv.clinicachavez.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TokenController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtRequestDto jwtRequestDto) throws Exception{

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequestDto.getUsername(),
                            jwtRequestDto.getPassword())
            );

        }catch (DisabledException e){
            throw new Exception("USER DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID CREDENTIALS", e);
        }

        final UserDetails userDetails = usuarioService.loadUserByUsername(jwtRequestDto.getUsername());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(jwtToken));

    }


}
