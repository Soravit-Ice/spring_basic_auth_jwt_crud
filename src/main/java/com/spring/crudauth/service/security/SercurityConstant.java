package com.spring.crudauth.service.security;

public interface SercurityConstant {
    String SECRET_KEY = "SorawitToken";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_AUTHORIZATION = "Authorization";
    String CLAIMS_ROLE = "role";
    long EXPIRATION_TIME = (5 * 60 * 1000);
}
