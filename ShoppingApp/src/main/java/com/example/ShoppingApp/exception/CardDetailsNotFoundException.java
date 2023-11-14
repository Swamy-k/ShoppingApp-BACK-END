package com.example.ShoppingApp.exception;

public class CardDetailsNotFoundException extends RuntimeException {
    public CardDetailsNotFoundException() {

    }

    public CardDetailsNotFoundException(String message) {
        super(message);
    }
}
