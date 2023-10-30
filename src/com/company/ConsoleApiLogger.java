package com.company;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleApiLogger {
    private AtomicInteger callCount = new AtomicInteger(0);

    public void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse) {
        callCount.incrementAndGet();
        ApiLog apiLog = new ApiLog();
        apiLog.setTimestamp(LocalDateTime.now());
        apiLog.setServiceCode(apiRequest.getServiceCode());
        apiLog.setResult(apiResponse.getResponseBody());
        // 在此处将apiLog对象写入日志
    }
}
