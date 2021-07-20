package com.spring.crudauth.service.security;


import com.spring.crudauth.Entity.UserEntity;
import com.spring.crudauth.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findUserByUserName(userName);
        if (Objects.nonNull(userEntity)) {
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority(userEntity.getRole()));

            List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>(roles);

            return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), authorityList);
        } else {
            throw new UsernameNotFoundException("Username :" + userName + "does not exist");
        }
    }
}
