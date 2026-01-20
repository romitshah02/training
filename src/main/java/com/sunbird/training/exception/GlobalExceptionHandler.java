package com.sunbird.training.exception;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sunbird.training.dao.ApiResponse;
import com.sunbird.training.dao.ResponseParams;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {

        System.out.println("Exception : "+ex.getMessage());

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString(),
            "failed",
            "RESOURCE_NOT_FOUND",
            ex.getMessage()
        );

        ApiResponse<Object> response = new ApiResponse<>(
            "api.error",
            "v1",
            LocalDate.now().toString(),
            params,
            "NOT_FOUND",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        
        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString(),
            "failed",
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred: " + ex.getMessage()
        );

        ApiResponse<Object> response = new ApiResponse<>(
            "api.error",
            "v1",
            LocalDate.now().toString(),
            params,
            "INTERNAL_SERVER_ERROR",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
