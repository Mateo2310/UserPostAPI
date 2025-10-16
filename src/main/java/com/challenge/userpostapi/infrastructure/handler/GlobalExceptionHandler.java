package com.challenge.userpostapi.infrastructure.handler;

import com.challenge.userpostapi.application.dto.ResponseGeneric;
import com.challenge.userpostapi.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseGeneric<>("error", ex.getMessage(), null));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidation(ValidationException ex) {
        return ResponseEntity.badRequest()
                .body(new ResponseGeneric<>("error", ex.getMessage(), null));
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        return ResponseEntity.badRequest().body(new ResponseGeneric<>("error", ex.getMessage(), null));
    }

    @ExceptionHandler(DatabaseUnavailableException.class)
    public ResponseEntity<?> handleDatabase(DatabaseUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseGeneric<>("error", ex.getMessage(), null));
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<?> handleDatabase(UnexpectedException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseGeneric<>("error", ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpected(Exception ex) {
        return ResponseEntity.internalServerError().body(new ResponseGeneric<>("error", ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(
                new ResponseGeneric<>("error", "Errores de validación", errors)
        );
    }

}
