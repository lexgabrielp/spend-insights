package com.spendinsights.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String, Object>> err(Exception e) {
        return ResponseEntity.badRequest().body(Map.of("timestamp", Instant.now().toString(), "error", e.getClass().getSimpleName(), "message", e.getMessage() == null ? "Request failed" : e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> val(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("message", "Validation failed", "fields", e.getBindingResult().getFieldErrors().stream().map(f -> f.getField() + ": " + f.getDefaultMessage()).toList()));
    }
}
