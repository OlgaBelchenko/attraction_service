package com.example.attraction_service.exception;

/**
 * Исключение, выбрасываемое в случае попытки добавления в базу данных уже существующего там местоположения.
 */

public class LocalityAlreadyExistsException extends RuntimeException {
    public LocalityAlreadyExistsException(String message) {
        super(message);
    }
}
