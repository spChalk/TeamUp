package com.example.socialnetworkingapp.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String s) {
        super(s);
    }
}
