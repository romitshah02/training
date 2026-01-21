package com.sunbird.training.exception;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sunbird.training.dao.ApiResponse;
import com.sunbird.training.dao.ResponseParams;


@RestControllerAdvice
public class GlobalExceptionHandler {

    //!For handling not found exceptions
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

    //!For general exceptions
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

    //!For validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserRequestException(MethodArgumentNotValidException ex) {
        
        // Extract validation error messages
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .reduce((msg1, msg2) -> msg1 + "; " + msg2)
            .orElse("Validation failed");

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString(),
            "failed",
            "VALIDATION_ERROR",
            errorMessage
        );

        ApiResponse<Object> response = new ApiResponse<>(
            "api.error",
            "v1",
            LocalDate.now().toString(),
            params,
            "BAD_REQUEST",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //!For enum validation exceptions
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserRequestEnumException(HttpMessageNotReadableException ex) {
        
        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString(),
            "failed",
            "BAD_REQUEST",
            "Error : " + ex.getMessage()
        );

        ApiResponse<Object> response = new ApiResponse<>(
            "api.error",
            "v1",
            LocalDate.now().toString(),
            params,
            "BAD_REQUEST",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


        //!For validation exceptions
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        
       
        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString(),
            "failed",
            "VALIDATION_ERROR",
            "Error : " + ex.getMessage()
        );

        ApiResponse<Object> response = new ApiResponse<>(
            "api.error",
            "v1",
            LocalDate.now().toString(),
            params,
            "BAD_REQUEST",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
