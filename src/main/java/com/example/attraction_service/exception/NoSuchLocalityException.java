package com.example.attraction_service.exception;

public class NoSuchLocalityException extends RuntimeException {
    public NoSuchLocalityException(String message) {
        super(message);
    }
}
