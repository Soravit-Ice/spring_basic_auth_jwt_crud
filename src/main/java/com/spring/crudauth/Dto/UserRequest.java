package com.spring.crudauth.Dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class UserRequest {


    @NotEmpty
    @Size(min = 5,max = 100)
    private String username;

    @NotEmpty
    @Size(min = 8,message = "Password must be at lest {min} char")
    private String password;

    @NotEmpty
    private String role;

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
