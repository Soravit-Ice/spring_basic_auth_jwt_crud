package com.spring.crudauth.exception;

public class UserDupicateException extends RuntimeException{
    public UserDupicateException(String username){
        super("Username:"+username+"is dupicate");
    }
}
