package com.company;

import com.company.service.ApiRequest;
import com.company.service.ApiResponse;

public interface ApiErrorHandler {
    void handleApiError(ApiError apiError);
    void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse);
}
