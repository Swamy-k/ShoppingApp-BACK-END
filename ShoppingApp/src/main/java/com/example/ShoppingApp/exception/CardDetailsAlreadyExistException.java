package com.example.ShoppingApp.exception;

public class CardDetailsAlreadyExistException extends RuntimeException {
    public CardDetailsAlreadyExistException() {

    }

    public CardDetailsAlreadyExistException(String message) {
        super(message);
    }

}
