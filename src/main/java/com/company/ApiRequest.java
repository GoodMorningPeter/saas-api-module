package com.company;

import java.util.Map;
import java.sql.Timestamp;

public class ApiRequest {
    private Api api;
    private ApiUser apiUser;
    private Timestamp timestamp;
    private Integer statusCode;

    public ApiRequest(Api api, ApiUser apiUser, Timestamp timestamp, Integer statusCode) {
        this.api = api;
        this.apiUser = apiUser;
        this.timestamp = timestamp;
        this.statusCode = statusCode;
    }

    public ApiRequest() {
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public ApiUser getApiUser() {
        return apiUser;
    }

    public void setApiUser(ApiUser apiUser) {
        this.apiUser = apiUser;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return statusCode;
    }

    public void setStatus(Integer status) {
        this.statusCode = status;
    }

    // getters and setters
}
