package com.company;

public class ApiErrorHandler {
    public void handle(ApiResponse response) {
        if ("Error".equals(response.getExecuteResult())) {
            System.err.println("Error code: " + response.getErrorCode());
            System.err.println("Error message: " + response.getErrorMessage());
        }
    }
}
