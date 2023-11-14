package com.example.ShoppingApp.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {

    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
