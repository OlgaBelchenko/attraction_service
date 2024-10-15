package com.example.attraction_service.exception;

/**
 * Исключение, выбрасываемое в случае попытки добавления в базу данных уже существующей там достопримечательности.
 */

public class AttractionAlreadyExistsException extends RuntimeException {
    public AttractionAlreadyExistsException(String message) {
        super(message);
    }
}
