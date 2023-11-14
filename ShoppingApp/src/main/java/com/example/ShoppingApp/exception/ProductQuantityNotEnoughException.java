package com.example.ShoppingApp.exception;

public class ProductQuantityNotEnoughException extends RuntimeException {

    public ProductQuantityNotEnoughException() {

    }

    public ProductQuantityNotEnoughException(String message) {
        super(message);
    }
}