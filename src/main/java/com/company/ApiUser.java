package com.company;

import java.util.List;

public class ApiUser {
    private String username;
    private String password;
    private List<String> permissions;

    public ApiUser(String username, String password, List<String> permissions) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    public ApiUser() {
    }

    public ApiUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    // 其他getter和setter方法
}
