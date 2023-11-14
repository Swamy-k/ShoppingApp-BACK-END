package com.example.ShoppingApp.exception;

public class InvalidLoginKeyException extends RuntimeException {
    public InvalidLoginKeyException() {

    }

    public InvalidLoginKeyException(String message) {
        super(message);
    }
}
