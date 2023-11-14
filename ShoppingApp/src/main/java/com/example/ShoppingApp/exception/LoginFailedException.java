package com.example.ShoppingApp.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {

    }

    public LoginFailedException(String message) {
        super(message);
    }
}
