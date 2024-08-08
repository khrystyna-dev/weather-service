package com.example.weatherapp.exception;

public class CustomDataValidationException extends RuntimeException {
    public CustomDataValidationException(String message) {
        super(message);
    }
}
