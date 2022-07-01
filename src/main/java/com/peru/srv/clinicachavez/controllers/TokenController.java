package com.peru.srv.clinicachavez.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peru.srv.clinicachavez.models.dto.NewTokenDTO;
import com.peru.srv.clinicachavez.models.dto.TokenPropertiesDTO;
import com.peru.srv.clinicachavez.models.entities.Rol;
import com.peru.srv.clinicachavez.models.entities.TokenProperties;
import com.peru.srv.clinicachavez.models.entities.Usuario;
import com.peru.srv.clinicachavez.service.ITokenService;
import com.peru.srv.clinicachavez.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.peru.srv.clinicachavez.security.utils.ConstantSecurity.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping(value = PATH_TOKEN)
public class TokenController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ITokenService tokenService;

    @PostMapping(value = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenProperties> crearToken(@Valid @RequestBody TokenPropertiesDTO tokenDto){
        Map<String, Object> response = new HashMap<>();
        TokenProperties tokenProperties = null;

        try {
            tokenProperties = tokenService.saveToken(tokenDto);
        }catch (Exception e){
            response.put("mensaje", "error al generar el token");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se genero el token");
        response.put("Token", tokenProperties);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/actualizar",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenProperties> actualizarToken(@Valid @RequestBody NewTokenDTO newTokenDto){
        Map<String, Object> response = new HashMap<>();
        TokenProperties tokenProperties = null;

        try {
            tokenProperties = tokenService.updateToken(newTokenDto);
        }catch (Exception e){
            response.put("mensaje", "error al actuaizar el token");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se actualizo el token");
        response.put("Token", tokenProperties);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TokenProperties>> obtenerTokens(){
        Map<String, Object> response = new HashMap<>();

        List<TokenProperties> allTokens = tokenService.getAllToken();

        response.put("mensaje", "Se obtuvo los tokens");
        response.put("Tokens", allTokens);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER)){
            try {
                String refreshToken = authorizationHeader.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String username = decodedJWT.getSubject();
                Usuario user = usuarioService.getUsuario(username);

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Rol::getTitulo).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
//                    response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("errorMessage", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("Refresh token is missing!");
        }
    }
}
