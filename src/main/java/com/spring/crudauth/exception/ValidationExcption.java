package com.spring.crudauth.exception;

public class ValidationExcption extends RuntimeException{
    public ValidationExcption(String message){
        super("Validation Error:" + message);
    }
}
