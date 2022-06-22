package com.peru.srv.clinicachavez.Filter;

import com.peru.srv.clinicachavez.service.impl.UsuarioServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private TokenManager tokenManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            token = tokenHeader.substring(7);

            try{
                username = tokenManager.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT token has expired");
            }

        }else {
            System.out.println("Bearer String not found in token");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = usuarioService.loadUserByUsername(username);

            if(tokenManager.validateJwtToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
