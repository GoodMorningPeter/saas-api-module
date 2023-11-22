package com.company.service;

import com.company.ApiError;

public class ApiResponse {
    private Integer id;  //Api的id
    private Integer statusCode;
    private String responseBody;
    private ApiError error;

    public ApiResponse(Integer id, Integer statusCode, String responseBody, ApiError error) {
        this.id = id;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.error = error;
    }

    public ApiResponse(String responseBody) {;
        this.responseBody = responseBody;
    }

    public ApiResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    // ...

}