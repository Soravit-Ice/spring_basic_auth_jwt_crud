package com.spring.crudauth.config.security;

import com.spring.crudauth.service.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationEntryPointJwtService authenticationEntryPointJwtService;
    private final AccessDeniedHandlerJwtService accessDeniedHandlerJwtService;

    public WebSecurity(CustomUserDetailsService customUserDetailsService,
                       BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationEntryPointJwtService authenticationEntryPointJwtService, AccessDeniedHandlerJwtService accessDeniedHandlerJwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationEntryPointJwtService = authenticationEntryPointJwtService;
        this.accessDeniedHandlerJwtService = accessDeniedHandlerJwtService;
    }


    //form login standard ไม่เอาแล้ว จะต้องจะใช้เป็น custom เอง
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/auth/register").permitAll()
                .antMatchers(HttpMethod.DELETE, "/service/product/**").hasAnyAuthority("admin").anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointJwtService).and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerJwtService).and()
                .addFilter(authenticationFilter()).
                sessionManagement().and()
                .addFilter(new JWTAuthorizationFilterService(authenticationManager())).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        final UsernamePasswordAuthenticationFilter filter = new JWTAuthenticationFiltterService(authenticationManager());
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

}
