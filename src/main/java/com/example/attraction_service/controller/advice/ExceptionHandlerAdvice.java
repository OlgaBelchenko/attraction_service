package com.example.attraction_service.controller.advice;

import com.example.attraction_service.dto.ErrorResponse;
import com.example.attraction_service.exception.NoSuchAttractionException;
import com.example.attraction_service.exception.NoSuchAttractionTypeException;
import com.example.attraction_service.exception.NoSuchLocalityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(NoSuchAttractionTypeException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchAttractionTypeException(NoSuchAttractionTypeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoSuchAttractionException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchAttractionException(NoSuchAttractionException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoSuchLocalityException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchLocalityException(NoSuchLocalityException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}
