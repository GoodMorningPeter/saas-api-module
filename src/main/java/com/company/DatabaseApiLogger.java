package com.company;
import com.company.entity.CallLog;
import com.company.entity.UsageStats;
import com.company.mapper.CallLogMapper;
import com.company.mapper.UsageStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;

@Service
public class DatabaseApiLogger implements ApiLogger {
    private Connection connection;

    @Autowired
    private CallLogMapper callLogMapper;
    private UsageStatsMapper usageStatsMapper;

    public DatabaseApiLogger(){// (Connection connection) {
//        this.connection = connection;
    }

    public void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse, long duration) {
        String call_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( apiRequest.getTimestamp() );
        CallLog calllog = new CallLog(null, "database", apiRequest.getApiUser().getUsername(), call_time, String.valueOf(duration), apiResponse.getError().getMessage());
        callLogMapper.insertLog(calllog);

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
        UsageStats usageStats = new UsageStats(null, "database", null);
        usageStatsMapper.updateStats(usageStats);
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