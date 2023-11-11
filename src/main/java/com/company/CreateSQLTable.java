package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateSQLTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/api";  // MySQL database
        String username = "morning"; // MySQL username
        String password = "123456";  // MySQL password

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            String createApiCallLogTable = "CREATE TABLE api_call_log (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "description VARCHAR(255)," +
                    "caller VARCHAR(255)," +
                    "call_time TIMESTAMP," +
                    "duration INT," +
                    "error_message VARCHAR(255)" +
                    ")";

            String createApiUsageStatsTable = "CREATE TABLE api_usage_stats (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "description VARCHAR(255)," +
                    "call_count INT" +
                    ")";

            statement.execute(createApiCallLogTable);
            statement.execute(createApiUsageStatsTable);

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}