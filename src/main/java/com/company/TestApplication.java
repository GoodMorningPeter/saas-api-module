package com.company;

import com.company.mapper.ApiMapper;
import com.company.mapper.CallLogMapper;
import com.company.mapper.UsageStatsMapper;
import com.company.service.ApiLogger;
import com.company.service.DatabaseApiLogger;
import com.company.service.ApiManager;
import com.company.service.MemoryApiManager;
import com.company.entity.Api;
import com.company.service.ApiInvoker;
import com.company.api.WeatherApiInvoker;
import com.company.api.LocationApiInvoker;
import com.company.service.ApiRequest;
import com.company.service.ApiResponse;
import com.company.entity.ApiUser;
import com.company.entity.UsageStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
public class TestApplication {
    @Autowired
    private CallLogMapper callLogMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private UsageStatsMapper usageStatsMapper;

    public static Integer totalApiCount = 0;

    public Api ApiSetUp(Integer id, String urlString, String ApiDescription){
        Api weatherApi = new Api();
        weatherApi.setId(id);
        weatherApi.setApiDescription(ApiDescription);
        weatherApi.setApiUrl(urlString);

        // Insert new Api record
        apiMapper.addApi(weatherApi);

        // Create new UsageStats
        UsageStats newStats = new UsageStats();
        newStats.setDescription(weatherApi.getApiDescription());
        newStats.setCall_count("0");

        // Insert new UsageStats record
        usageStatsMapper.insertStats(newStats);

        return weatherApi;
    }

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        // Start the Spring application
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);

        // Use Spring to get an instance of DatabaseApiLogger
        ApiLogger logger = context.getBean(DatabaseApiLogger.class);

        // Get an instance of TestApplication
        TestApplication app = context.getBean(TestApplication.class);

        ApiManager apiManager = new MemoryApiManager();

        // Register WeatherApi
        Api weatherApi = app.ApiSetUp(++totalApiCount,"http://t.weather.itboy.net/api/weather/city/101250601", "weather");
        apiManager.registerApi(weatherApi);
        // Invoke WeatherAPi and get Response
        ApiInvoker weatherApiInvoker = new WeatherApiInvoker(apiManager);

        // Register LocationApi
        Api locationApi = app.ApiSetUp(++totalApiCount, "https://restapi.amap.com/v3/geocode/geo?address=北京市&key=1137f7d2f7a524537f4bd8f10c87ac2d", "location");
        apiManager.registerApi(locationApi);
        // Invoke LocationApi and get Response
        ApiInvoker locationApiInvoker = new LocationApiInvoker(apiManager);
        // ApiRequest apiRequest_loc = new ApiRequest();
        // apiRequest_loc.setServiceCode("location");
        // ApiResponse apiResponse_loc = locationApiInvoker.invokeApi(apiRequest_loc, logger);
        // System.out.println(apiResponse_loc.getResponseBody());

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApi(weatherApi);
        apiRequest.setApiUser(new ApiUser("Tom", "123456"));
        apiRequest.setTimestamp(timestamp);
        apiRequest.setStatus(0);

        ApiResponse apiResponse = weatherApiInvoker.invokeApi(apiRequest, logger);
        ApiResponse apiResponse_loc = locationApiInvoker.invokeApi(apiRequest, logger);
        System.out.println(apiResponse.getResponseBody());
        System.out.println(apiResponse_loc.getResponseBody());

        // Register LocationApi
    }
}