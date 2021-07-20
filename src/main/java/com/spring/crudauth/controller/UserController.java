package com.spring.crudauth.controller;

import com.spring.crudauth.Dto.UserRequest;
import com.spring.crudauth.Entity.UserEntity;
import com.spring.crudauth.exception.ValidationExcption;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.crudauth.service.UserService;

import javax.validation.Valid;
import javax.validation.ValidationException;


@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public UserEntity register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                throw new ValidationExcption(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            });
        }
        return userService.register(userRequest);
    }
}
