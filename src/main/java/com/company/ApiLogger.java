package com.company;

public interface ApiLogger {
    void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse, long duration);
    void updateApiUsageStats(Integer apiId);
}

