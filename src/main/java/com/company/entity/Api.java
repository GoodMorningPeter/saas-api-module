package com.company.entity;

import java.util.List;

public class Api {
    private Integer id;
    private String ApiDescription;
    private String ApiUrl;
    private String appUser;
    private String appKey;
    private List<String> requiredPermissions;

    public Api(Integer id, String apiDescription, String apiUrl, String appUser, String appKey) {
        this.id = id;
        ApiDescription = apiDescription;
        ApiUrl = apiUrl;
        this.appUser = appUser;
        this.appKey = appKey;
    }

    public Api() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiDescription() {
        return ApiDescription;
    }

    public void setApiDescription(String apiDescription) {
        ApiDescription = apiDescription;
    }

    public String getApiUrl() {
        return ApiUrl;
    }

    public void setApiUrl(String apiUrl) {
        ApiUrl = apiUrl;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public List<String> getRequiredPermissions() {
        return requiredPermissions;
    }

    public void setRequiredPermissions(List<String> requiredPermissions) {
        this.requiredPermissions = requiredPermissions;
    }

    // getters and setters
    // ...
}
