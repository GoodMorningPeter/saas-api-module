package com.company.service;

import org.springframework.stereotype.Service;

@Service
public interface ApiLogger {
    void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse, long duration);
    void updateApiUsageStats(Integer apiId);
}

