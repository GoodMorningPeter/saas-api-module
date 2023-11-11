package com.company;
import java.sql.*;
import java.time.LocalDateTime;


public class DatabaseApiLogger implements ApiLogger {
    private Connection connection;

    public DatabaseApiLogger(Connection connection) {
//        this.connection = connection;
    }

    public void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse, long duration) {
//        String sql = "INSERT INTO api_call_log (api_id, caller, call_time, duration, error_message) VALUES (?, ?, ?, ?, ?)";
//        try {
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, apiRequest.getServiceCode());
//            statement.setString(2, apiRequest.getApiUser().getUsername());
//            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//            statement.setLong(4, duration);
//            if(apiResponse.getError() != null) {
//                statement.setString(5, apiResponse.getError().getMessage());
//            } else {
//                statement.setString(5, null);
//            }
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            // Handle the error
//        }
    }

    public void updateApiUsageStats(Integer apiId) {
//        String sql = "INSERT INTO api_usage_stats (api_id, call_count) VALUES (?, 1) ON DUPLICATE KEY UPDATE call_count = call_count + 1";
//        try {
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, apiId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}