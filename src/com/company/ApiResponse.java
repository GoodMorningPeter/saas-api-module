package com.company;

public class ApiResponse {
    private String executeResult;
    private String errorCode;
    private String errorMessage;
    private String data;

    public String getExecuteResult() {
        return "Error";
    }

    public String getErrorCode() {
        return "This is error code";
    }

    public String getErrorMessage() {
        return "This is error Message";
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setData(String data) {
        this.data = data;
    }

    // getters and setters
    // ...

}