package com.company;

public interface ApiErrorHandler {
    void handleApiError(ApiError apiError);
    void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse);
}
