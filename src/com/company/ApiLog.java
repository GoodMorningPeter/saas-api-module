package com.company;
import java.time.LocalDateTime;

public class ApiLog {
    private LocalDateTime timestamp;
    private String serviceCode;
    private String result;

    // getters and setters...

    public ApiLog(String serviceCode, String result) {
        this.timestamp = LocalDateTime.now();
        this.serviceCode = serviceCode;
        this.result = result;
    }
}
