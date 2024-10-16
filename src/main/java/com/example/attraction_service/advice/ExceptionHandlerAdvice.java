package com.example.attraction_service.advice;

import com.example.attraction_service.dto.response.ErrorResponse;
import com.example.attraction_service.exception.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Класс для обработки исключений в REST-контроллерах.
 * Класс содержит несколько методов, каждый из которых обрабатывает конкретный тип исключения. Методы возвращают объект ResponseEntity,
 * который содержит информацию об ошибке и код состояния HTTP.
 * Методы класса:
 * handleNoSuchAttractionTypeException - обрабатывает исключение {@link NoSuchAttractionTypeException}
 * handleNoSuchAttractionException - обрабатывает исключение {@link NoSuchAttractionException}
 * handleAttractionAlreadyExistsException - обрабатывает исключение {@link AttractionAlreadyExistsException}
 * handleLocalityAlreadyExistsException - обрабатывает исключение {@link LocalityAlreadyExistsException}
 * handleNoSuchElementException - обрабатывает исключение {@link NoSuchElementException}
 * handleNoSuchLocalityException - обрабатывает исключение {@link NoSuchLocalityException}
 * handleInvalidSortParameterException - обрабатывает исключение {@link InvalidSortParameterException}
 * handleValidationExceptions - обрабатывает исключение {@link MethodArgumentNotValidException} и возвращает информацию об ошибках валидации
 */

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NoSuchAttractionTypeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoSuchAttractionTypeException(NoSuchAttractionTypeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoSuchAttractionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoSuchAttractionException(NoSuchAttractionException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AttractionAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleAttractionAlreadyExistsException(AttractionAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(LocalityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleLocalityAlreadyExistsException(LocalityAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }
    @ExceptionHandler(NoSuchLocalityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoSuchLocalityException(NoSuchLocalityException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidSortParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidSortParameterException(InvalidSortParameterException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Object dto = ex.getBindingResult().getTarget();
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();

                String jsonFieldName = getJsonFieldName(dto, fieldName);

                String errorMessage = error.getDefaultMessage();
                errors.put(jsonFieldName != null ? jsonFieldName : fieldName, errorMessage);
            }
        });

        return ResponseEntity.badRequest().body(errors);
    }

    private String getJsonFieldName(Object dto, String fieldName) {
        try {
            Field field = dto.getClass().getDeclaredField(fieldName);
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            return annotation != null ? annotation.value() : null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
