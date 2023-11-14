package com.company.entity;

public class UsageStats {
    private Integer id;
    private String description;
    private String call_count;


    public UsageStats(Integer id, String description, String call_count) {
        this.id = id;
        this.description = description;
        this.call_count = call_count;
    }

    public UsageStats() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCall_count() {
        return call_count;
    }

    public void setCall_count(String call_count) {
        this.call_count = call_count;
    }
}
