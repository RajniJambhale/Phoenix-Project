package com.example.phoenixcodecrafterproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        // 404 - User not found
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<Map<String, Object>> handleUserNotFound(
                UserNotFoundException ex) {

            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("timestamp", LocalDateTime.now());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(PostNotFoundException.class)
        public ResponseEntity<Map<String, Object>> handlePostNotFound(PostNotFoundException ex) {

            Map<String, Object> response = new HashMap<>();
            response.put("timestamp", LocalDateTime.now());
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("error", "Not Found");
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        // REQUIRED for List<@Valid User> in Spring Boot 3+
        @ExceptionHandler(HandlerMethodValidationException.class)
        public ResponseEntity<Map<String, String>> handleHandlerMethodValidation(
                HandlerMethodValidationException ex) {

            Map<String, String> errors = new HashMap<>();

            ex.getAllErrors().forEach(error -> {
                if (error instanceof FieldError fieldError) {
                    errors.put(fieldError.getField(),
                            fieldError.getDefaultMessage());
                } else {
                    // fallback for object-level errors
                    errors.put(error.getCodes()[0],
                            error.getDefaultMessage());
                }
            });

            return ResponseEntity.badRequest().body(errors);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidationErrors(
                MethodArgumentNotValidException ex) {

            Map<String, String> fieldErrors = new HashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error ->
                    fieldErrors.put(error.getField(), error.getDefaultMessage())
            );

            Map<String, Object> response = new HashMap<>();
            response.put("status", 400);
            response.put("errors", fieldErrors);

            return ResponseEntity.badRequest().body(response);
        }

    }
