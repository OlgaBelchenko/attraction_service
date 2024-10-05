package com.example.attraction_service.exception;

public class NoSuchAttractionException extends RuntimeException {
    public NoSuchAttractionException(String message) {
        super(message);
    }
}
