package com.example.ShoppingApp.exception;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException() {

    }

    public EmptyCartException(String message) {
        super(message);
    }
}
