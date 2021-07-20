package com.spring.crudauth.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.crudauth.Dto.UserRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static com.spring.crudauth.service.security.SercurityConstant.EXPIRATION_TIME;

public class JWTAuthenticationFiltterService extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFiltterService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //set path match path login?
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }


    //ตัวจัดการว่า request ที่เข้ามาตรวจสอบว่า login ถูกไหมถ้าถูก -> success method
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UserRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
                    userRequest.getPassword(), new ArrayList<>()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if (Objects.nonNull(authResult.getPrincipal())) {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

            String username = user.getUsername();
            if (username != null && username.length() > 0) {
                Claims claims = Jwts.claims().setSubject(username)
                        .setIssuer("Sorawit")
                        .setAudience("www.sorawit-dev.com");

                List<String> roles = new ArrayList<>();
                user.getAuthorities().stream().forEach(
                        authority ->{
                            roles.add(authority.getAuthority());
                        }
                );
                claims.put("role",roles);
                claims.put("value","product");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Map<String , Object> responseJson = new HashMap<>();
                responseJson.put("token" , createToken(claims));

                OutputStream out = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(out,responseJson);

                out.flush();
            }
        }
    }

    private String createToken(Claims claims){
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,"SorawitToken").compact();
    }
}
