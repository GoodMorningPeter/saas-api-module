package com.company;

import com.company.ApiError;
import com.company.service.ApiResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@ComponentScan
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setDetails(ex.toString());
        error.setTimestamp(LocalDateTime.now());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFileNotFoundException(FileNotFoundException ex) {
        ApiError error = new ApiError();
        error.setMessage("Requested resource not found");
        error.setDetails(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setDetails(ex.getCause().toString());
        error.setTimestamp(LocalDateTime.now());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<ApiResponse> handleUnknownHostException(UnknownHostException ex) {
        ApiError error = new ApiError();
        error.setMessage("Unable to resolve host");
        error.setDetails(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}