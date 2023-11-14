package com.example.ShoppingApp.exceptionhadler;


import com.example.ShoppingApp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class ShoppingAppExceptionHandler {

    //Seller Exceptions
    @org.springframework.web.bind.annotation.ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<ErrorDetails> sellerHandler(SellerNotFoundException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SellerAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> sellerHandler(SellerAlreadyExistException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    //Customer Exceptions

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> customerExists(CustomerAlreadyExistException error, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorDetails> customerNotFound(CustomerNotFoundException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    //Card Exceptions
    @org.springframework.web.bind.annotation.ExceptionHandler(CardDetailsAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> cardAlreadyExists(CardDetailsAlreadyExistException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request as card already exists with the customer", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CardDetailsNotFoundException.class)
    public ResponseEntity<ErrorDetails> cardDetailsNotFound(CardDetailsNotFoundException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request as card details not found with the customer", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }


    //Validation Exceptions

    //This exception takes care of the invalid inputs
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> myMANVExceptionHandler(MethodArgumentNotValidException manve) {
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation Error", Objects.requireNonNull(manve.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }

    //This method checks for  invalid URIs
    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetails> noHandler(NoHandlerFoundException noHandler) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "NOT FOUND", "Not a Valid URL");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //This method checks for invalid HTTP methods
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDetails> httpRequestMethodException(HttpRequestMethodNotSupportedException exception) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED.value(), "Check the http method", exception.getMessage());
        return new ResponseEntity<ErrorDetails>(errorDetail, HttpStatus.METHOD_NOT_ALLOWED);
    }


    //Product Exceptions
    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> productHandler(ProductNotFoundException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<ErrorDetails> productHandler(ProductNotAvailableException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductQuantityNotEnoughException.class)
    public ResponseEntity<ErrorDetails> productHandler(ProductQuantityNotEnoughException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    //Address Exceptions
    @org.springframework.web.bind.annotation.ExceptionHandler(AddressAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> sellerHandler(AddressAlreadyExistException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorDetails> sellerHandler(AddressNotFoundException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }


    //Login Exceptions
    @org.springframework.web.bind.annotation.ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorDetails> sellerHandler(LoginFailedException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidLoginKeyException.class)
    public ResponseEntity<ErrorDetails> sellerHandler(InvalidLoginKeyException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    //Order Exceptions
    @org.springframework.web.bind.annotation.ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorDetails> orderHandler(EmptyCartException error, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request", error.getMessage());
        return new ResponseEntity<ErrorDetails>(errorDetail, HttpStatus.BAD_REQUEST);
    }


}