package com.example.ShoppingApp.exception;

public class SellerAlreadyExistException extends RuntimeException {
    public SellerAlreadyExistException() {

    }

    public SellerAlreadyExistException(String message) {
        super(message);
    }
}
