package com.peru.srv.clinicachavez.security.filter;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        System.out.println("Path " +  request.getServletPath());
        response.getWriter().write(new JSONObject()
                .put("timestamp", LocalDate.now())
                .put("status",HttpServletResponse.SC_UNAUTHORIZED)
                .put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase())
                .put("message", authException.getMessage().toUpperCase())
                .put("path", request.getServletPath())
                .toString());
    }
}
