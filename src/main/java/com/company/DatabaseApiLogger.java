package com.company;
import com.company.entity.CallLog;
import com.company.entity.UsageStats;
import com.company.service.CallLogService;
import com.company.service.UsageStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;

@Service
public class DatabaseApiLogger implements ApiLogger {
    @Autowired
    private CallLogService callLogService;
    @Autowired
    private UsageStatsService usageStatsService;

    public DatabaseApiLogger(){
    }

    public void logApiCall(ApiRequest apiRequest, ApiResponse apiResponse, long duration) {
        String call_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( apiRequest.getTimestamp() );
        CallLog calllog = new CallLog(null, apiRequest.getApi().getApiDescription(), apiRequest.getApiUser().getUsername(), call_time, String.valueOf(duration), apiResponse.getError().getMessage());
        callLogService.insertLog(calllog);

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
        UsageStats usageStats = new UsageStats(null, apiId, null);
        usageStatsService.updateStats(usageStats);

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