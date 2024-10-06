package com.example.attraction_service.exception;

import com.example.attraction_service.entity.Attraction;

/**
 * Исключение, бросаемое в случае отсутствия искомого объекта {@link Attraction} в базе данных.
 */

public class NoSuchAttractionException extends RuntimeException {
    public NoSuchAttractionException(String message) {
        super(message);
    }
}
