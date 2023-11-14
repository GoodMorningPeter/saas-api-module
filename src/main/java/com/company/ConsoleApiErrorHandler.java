package com.company;

import com.company.service.ApiRequest;
import com.company.service.ApiResponse;

public class ConsoleApiErrorHandler implements ApiErrorHandler {
    public void handleApiError(ApiError apiError) {
        System.err.println("API error: " + apiError.getMessage());
    }

    public void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse) {
        System.out.println("API call:");
        System.out.println("Request: " + apiRequest);
        System.out.println("Response: " + apiResponse);
    }
}