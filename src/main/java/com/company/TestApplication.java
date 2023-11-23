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
import com.company.api.BirthdayApiInvoker;
import com.company.api.LocationApiInvoker;
import com.company.api.TodayAnalysisInvoker;
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
import java.util.Arrays;

@SpringBootApplication
public class TestApplication {
    @Autowired
    private CallLogMapper callLogMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private MemoryApiManager apiManager;

    @Autowired
    private UsageStatsMapper usageStatsMapper;

    @Autowired
    private WeatherApiInvoker weatherApiInvoker;

    public static Integer totalApiCount = 0;

    public Api ApiSetUp(Integer id, String urlString, String ApiDescription){
        Api weatherApi = new Api();
        weatherApi.setId(id);
        weatherApi.setApiDescription(ApiDescription);
        weatherApi.setApiUrl(urlString);
        weatherApi.setRequiredPermissions(Arrays.asList("1", "2", "3"));
        if(ApiDescription.equals("today")){
            weatherApi.setRequiredPermissions(Arrays.asList("1", "2", "3", "4"));
        }

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

        // Register WeatherApi
        Api weatherApi = app.ApiSetUp(++totalApiCount,"http://t.weather.itboy.net/api/weather/city/101250601", "weather6");
        app.apiManager.registerApi(weatherApi);

        // Register LocationApi
        Api locationApi = app.ApiSetUp(++totalApiCount, "https://restapi.amap.com/v3/geocode/geo?address=北京市&key=1137f7d2f7a524537f4bd8f10c87ac2d", "location6");
        app.apiManager.registerApi(locationApi);
        // Invoke LocationApi and get Response
        ApiInvoker locationApiInvoker = new LocationApiInvoker(app.apiManager);

        // Register TodayApi
        Api todayApi = app.ApiSetUp(++totalApiCount, "http://apis.juhe.cn/fapig/calendar/day?date=2023-10-01&detail=1&key=baaaa4a6ebd7f03d1c95541b3720a906", "today6");
        app.apiManager.registerApi(todayApi);
        // Invoke TodayApi and get Response
        ApiInvoker todayApiInvoker = new TodayAnalysisInvoker(app.apiManager);


        // Register BirthdayApi
        Api birthdayApi = app.ApiSetUp(++totalApiCount, "http://v.juhe.cn/calendar/day?date=2002-2-11&key=9b5336bc498abd9b62542fbfb0eca843", "birthday6");
        app.apiManager.registerApi(birthdayApi);
        // Invoke BirthdayApi and get Response
        ApiInvoker birthdayApiInvoker = new BirthdayApiInvoker(app.apiManager);
        

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApi(weatherApi);
        apiRequest.setApiUser(new ApiUser("Tom", "123456", Arrays.asList("1", "2", "3")));
        apiRequest.setTimestamp(timestamp);
        apiRequest.setStatus(0);

        ApiRequest apiRequest_loc = new ApiRequest();
        apiRequest_loc.setApi(locationApi);
        apiRequest_loc.setApiUser(new ApiUser("Tom", "123456", Arrays.asList("1", "2", "3")));
        apiRequest_loc.setTimestamp(timestamp);
        apiRequest_loc.setStatus(0);

        ApiRequest apiRequest_today = new ApiRequest();
        apiRequest_today.setApi(todayApi);
        apiRequest_today.setApiUser(new ApiUser("Tom", "123456", Arrays.asList("1", "2", "3")));
        apiRequest_today.setTimestamp(timestamp);
        apiRequest_today.setStatus(0);

        ApiRequest apiRequest_birth = new ApiRequest();
        apiRequest_birth.setApi(birthdayApi);
        apiRequest_birth.setApiUser(new ApiUser("Tom", "123456", Arrays.asList("1", "2", "3")));
        apiRequest_birth.setTimestamp(timestamp);
        apiRequest_birth.setStatus(0);

        ApiResponse apiResponse = app.weatherApiInvoker.invokeApi(apiRequest, logger);
        ApiResponse apiResponse_loc = locationApiInvoker.invokeApi(apiRequest_loc, logger);
        ApiResponse apiResponse_today = todayApiInvoker.invokeApi(apiRequest_today, logger);
        ApiResponse apiResponse_birth = birthdayApiInvoker.invokeApi(apiRequest_birth, logger);
        System.out.println(apiResponse.getResponseBody());
        System.out.println(apiResponse_loc.getResponseBody());
        System.out.println(apiResponse_today.getResponseBody());
        System.out.println(apiResponse_birth.getResponseBody());
        

    }
}