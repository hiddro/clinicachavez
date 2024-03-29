package com.peru.srv.clinicachavez.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peru.srv.clinicachavez.models.entities.TokenProperties;
import com.peru.srv.clinicachavez.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.peru.srv.clinicachavez.security.utils.ConstantSecurity.*;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.*;

import static com.peru.srv.clinicachavez.security.utils.ConstantSecurity.*;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final ITokenService tokenService;

    public CustomAuthorizationFilter(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        TokenProperties tokenProperties = tokenService.getAllToken().get(0);

        if(request.getServletPath().equals(PATH_LOGIN) || request.getServletPath().equals(PATH_TOKEN + "/refresh")){
            filterChain.doFilter(request, response);
        }else{
            String authorizationHeader = request.getHeader(AUTHORIZATION);

            if(authorizationHeader != null && authorizationHeader.startsWith(BEARER)){
                try {
                    String token = authorizationHeader.substring(BEARER.length());
                    Algorithm algorithm = Algorithm.HMAC256(tokenProperties.getToken().getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim(CLAIM_ROLES).asArray(String.class);
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                            null,
                            authorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                }catch (Exception e){
                    log.error("Error logging in: {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
//                    response.sendError(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("errorMessage", e.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }

}
