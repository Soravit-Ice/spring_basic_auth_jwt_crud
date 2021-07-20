package com.spring.crudauth.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.crudauth.Dto.ApiStatusOut;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandlerJwtService implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiStatusOut apiStatusOut = new ApiStatusOut();

        apiStatusOut.setCode(String.valueOf(HttpServletResponse.SC_FORBIDDEN));
        apiStatusOut.setMessage("You don't have permission to call this !");

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), apiStatusOut);
    }
}
