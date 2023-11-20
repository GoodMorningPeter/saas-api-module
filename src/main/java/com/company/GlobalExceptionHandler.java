package com.company;

import com.company.ApiError;
import com.company.service.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleException(Exception ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setDetails(ex.toString());
        error.setTimestamp(LocalDateTime.now());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);
        return apiResponse;
    }
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleFileNotFoundException(FileNotFoundException ex) {
        ApiError error = new ApiError();
        error.setMessage("Requested resource not found");
        error.setDetails(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);
        return apiResponse;
    }
}
