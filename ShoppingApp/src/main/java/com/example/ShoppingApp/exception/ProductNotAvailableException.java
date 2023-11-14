package com.example.ShoppingApp.exception;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException() {

    }

    public ProductNotAvailableException(String message) {
        super(message);
    }
}
