package com.company;
import java.time.LocalDateTime;

public class ApiLog {
    private LocalDateTime timestamp;
    private String serviceCode;
    private String result;

    // getters and setters...

    public ApiLog() {

    }

    public ApiLog(String serviceCode, String result) {
        this.timestamp = LocalDateTime.now();
        this.serviceCode = serviceCode;
        this.result = result;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
