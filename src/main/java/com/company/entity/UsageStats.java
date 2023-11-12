package com.company.entity;

public class UsageStats {
    private Integer id;
    private String api_description;
    private String call_count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApi_description() {
        return api_description;
    }

    public void setApi_description(String api_description) {
        this.api_description = api_description;
    }

    public String getCall_count() {
        return call_count;
    }

    public void setCall_count(String call_count) {
        this.call_count = call_count;
    }

    public UsageStats(Integer id, String api_id, String call_count) {
        this.id = id;
        this.api_description = api_id;
        this.call_count = call_count;
    }
}
