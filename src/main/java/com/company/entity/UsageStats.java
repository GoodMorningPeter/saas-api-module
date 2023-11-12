package com.company.entity;

public class UsageStats {
    private Integer id;
    private Integer api_id;
    private String call_count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApi_id() {
        return api_id;
    }

    public void setApi_id(Integer api_id) {
        this.api_id = api_id;
    }

    public String getCall_count() {
        return call_count;
    }

    public void setCall_count(String call_count) {
        this.call_count = call_count;
    }

    public UsageStats(Integer id, Integer api_id, String call_count) {
        this.id = id;
        this.api_id = api_id;
        this.call_count = call_count;
    }
}
