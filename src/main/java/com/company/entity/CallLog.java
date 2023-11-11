package com.company.entity;

public class CallLog {
    private Integer id;
    private String description;
    private String caller;
    private String call_time;
    private String duration;
    private String error_message;


    public CallLog(Integer id, String description, String caller, String call_time, String duration, String error_message) {
        this.id = id;
        this.description = description;
        this.caller = caller;
        this.call_time = call_time;
        this.duration = duration;
        this.error_message = error_message;
    }

    public CallLog() {
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

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCall_time() {
        return call_time;
    }

    public void setCall_time(String call_time) {
        this.call_time = call_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}


