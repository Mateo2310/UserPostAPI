package com.challenge.userpostapi.infrastructure.handler;

import com.challenge.userpostapi.application.dto.ResponseGeneric;
import com.challenge.userpostapi.domain.exception.BusinessException;
import com.challenge.userpostapi.domain.exception.DatabaseUnavailableException;
import com.challenge.userpostapi.domain.exception.UnexpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
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
}
