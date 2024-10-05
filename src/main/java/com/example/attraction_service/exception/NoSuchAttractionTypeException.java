package com.example.attraction_service.exception;

public class NoSuchAttractionTypeException extends RuntimeException {
    public NoSuchAttractionTypeException(String message) {
        super(message);
    }
}
