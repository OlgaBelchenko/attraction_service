package com.example.attraction_service.exception;

import com.example.attraction_service.entity.AttractionType;

/**
 * Исключение, бросаемое в случае отсутствия искомого перечисления {@link AttractionType}.
 */

public class NoSuchAttractionTypeException extends RuntimeException {
    public NoSuchAttractionTypeException(String message) {
        super(message);
    }
}
