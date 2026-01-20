package com.example.phoenixcodecrafterproject.exception;
import com.example.phoenixcodecrafterproject.dto.request.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

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

        // Handle Resource Not Found (404)
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFound(
                ResourceNotFoundException ex) {

            ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.value(),   // 400
                    ex.getMessage(),
                    "Resource not found"
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // Handle custom BadRequestException
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(
                BadRequestException ex, WebRequest request) {

            ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),   // 400
                    ex.getMessage(),
                    "Bad request"
            );

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // Handle Duplicate Resource (409)
        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ErrorResponse> handleDuplicateResource(
                DuplicateResourceException ex) {

            ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(),   // 409
                    ex.getMessage(),
                    "Duplicate resource"
            );
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        // Handle all uncaught exceptions (500)
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGlobalException(
                Exception ex, WebRequest request) {

            ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                    "Internal server error",
                    "Something went wrong"
            );

            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
