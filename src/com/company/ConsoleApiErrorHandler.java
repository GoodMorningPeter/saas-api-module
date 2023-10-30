package com.company;

public class ConsoleApiErrorHandler implements ApiErrorHandler {
    public void handleApiError(ApiError apiError) {
        System.err.println("API error: " + apiError.getErrorMessage());
    }

    public void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse) {
        System.out.println("API call:");
        System.out.println("Request: " + apiRequest);
        System.out.println("Response: " + apiResponse);
    }
}