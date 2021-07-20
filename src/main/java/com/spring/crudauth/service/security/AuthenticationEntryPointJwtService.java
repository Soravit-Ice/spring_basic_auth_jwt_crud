package com.spring.crudauth.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.crudauth.Dto.ApiStatusOut;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointJwtService implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiStatusOut apiStatusOut = new ApiStatusOut();

        apiStatusOut.setCode(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
        apiStatusOut.setMessage("Pls Login First!");

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), apiStatusOut);

    }
}
