package com.spring.crudauth.config;

import com.spring.crudauth.service.security.AccessDeniedHandlerJwtService;
import com.spring.crudauth.service.security.AuthenticationEntryPointJwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class AppConfig {
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationEntryPointJwtService authenticationEntryPointJwtService(){
        return new AuthenticationEntryPointJwtService();
    }

    @Bean
    AccessDeniedHandlerJwtService accessDeniedHandlerJwtService(){
        return new AccessDeniedHandlerJwtService();
    }
}
