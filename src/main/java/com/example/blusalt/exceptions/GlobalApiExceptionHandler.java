package com.example.blusalt.exceptions;

import com.example.blusalt.models.dtos.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalApiExceptionHandler {

    @Autowired
    private Environment env;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Invalid input format";
        Map<String, String> errors = new HashMap<>();

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) ex.getCause();

            if (ife.getTargetType().isEnum()) {
                String fieldName = ife.getPath().get(0).getFieldName();
                String invalidValue = ife.getValue().toString();
                String allowedValues = Arrays.toString(ife.getTargetType().getEnumConstants());

                message = String.format("Invalid value '%s' for field '%s'", invalidValue, fieldName);
                errors.put(fieldName, "Must be one of: " + allowedValues);
            }
        }

        ApiResponse<Object> response = new ApiResponse<>(
                "failed",
                message,
                null,
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract field-specific error messages
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Wrap in ApiResponse
        ApiResponse<Object> response = new ApiResponse<Object>(
                "failed",
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                null,
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex) {
        boolean isDev = Arrays.asList(env.getActiveProfiles()).contains("dev");
        ApiResponse<Object> response = new ApiResponse<Object>(
                "failed",
                ex.getMessage(),
                null,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException ex) {
        boolean isDev = Arrays.asList(env.getActiveProfiles()).contains("dev");
        ApiResponse<Object> response = new ApiResponse<Object>(
                "failed",
                ex.getMessage(),
                null,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        boolean isDev = Arrays.asList(env.getActiveProfiles()).contains("dev");
        ApiResponse<Object> response = new ApiResponse<Object>(
                "failed",
                "An error occurred",
                null,
                isDev ? ex.getMessage() : null
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
