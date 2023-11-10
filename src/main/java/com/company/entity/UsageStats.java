package com.company.entity;

public class UsageStats {
    private String id;
    private String api_id;
    private String call_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApi_id() {
        return api_id;
    }

    public void setApi_id(String api_id) {
        this.api_id = api_id;
    }

    public String getCall_count() {
        return call_count;
    }

    public void setCall_count(String call_count) {
        this.call_count = call_count;
    }

    public UsageStats(String id, String api_id, String call_count) {
        this.id = id;
        this.api_id = api_id;
        this.call_count = call_count;
    }
}
