package com.company;

import java.util.Map;

public class ApiRequest {
    private String serviceCode;
    private ApiUser apiUser;
    private Map<String, String> parameters;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public ApiUser getApiUser() {
        return apiUser;
    }

    public void setApiUser(ApiUser apiUser) {
        this.apiUser = apiUser;
    }
    // getters and setters
}
