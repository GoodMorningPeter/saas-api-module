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
import java.net.URL;
import java.util.Map;

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

    public static TestApplication app;
    public static Integer totalApiCount = 0;

    public Api ApiSetUp(Integer id, String urlString, String ApiDescription){
        Api exmpApi = new Api();
        exmpApi.setId(id);
        exmpApi.setApiDescription(ApiDescription);
        exmpApi.setApiUrl(urlString);
        exmpApi.setRequiredPermissions(Arrays.asList("1", "2", "3"));
//        if(ApiDescription.equals("today")){
//            exmpApi.setRequiredPermissions(Arrays.asList("1", "2", "3", "4"));
//        }

        // Insert new Api record
        apiMapper.addApi(exmpApi);

        // Create new UsageStats
        UsageStats newStats = new UsageStats();
        newStats.setDescription(exmpApi.getApiDescription());
        newStats.setCall_count("0");

        // Insert new UsageStats record
        usageStatsMapper.insertStats(newStats);

        return exmpApi;
    }
    static public class APIurlGenerator {
        private String apiUrl;
        private String apiName;
        public Api expApi;
        APIurlGenerator(String apiUrlspe, Map<String, String> parameters, String apiNamespe)
        {
            apiUrl = ConstructAPIurl(apiUrlspe, parameters);
            apiName = apiNamespe;
            RegisterApi();
            return;
        }
        private String ConstructAPIurl(String apiUrl, Map<String, String> parameters) {
            try {
                // 构建完整的 URL
                StringBuilder urlBuilder = new StringBuilder(apiUrl);
                if (parameters != null && !parameters.isEmpty()) {
                    urlBuilder.append("?");
                    for (Map.Entry<String, String> entry : parameters.entrySet()) {
                        urlBuilder.append(entry.getKey())
                                .append("=")
                                .append(entry.getValue())
                                .append("&");
                    }
                    urlBuilder.deleteCharAt(urlBuilder.length() - 1); // 移除最后一个 "&"
                }
                URL url = new URL(urlBuilder.toString());
                return url.toString();
            } catch (Exception exp){
                exp.printStackTrace();
                return null;
            }
        }
        private void RegisterApi(){
            expApi = app.ApiSetUp(++totalApiCount, apiUrl, apiName);
            app.apiManager.registerApi(expApi);
            return;
        }
        public Api getExpApi(){
            return expApi;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        // Start the Spring application
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);

        // Use Spring to get an instance of DatabaseApiLogger
        ApiLogger logger = context.getBean(DatabaseApiLogger.class);

        // Get an instance of TestApplication
        app = context.getBean(TestApplication.class);

        // Register Api
        String todayUrl = "http://apis.juhe.cn/fapig/calendar/day";
        Map<String, String> todayParm = Map.of(
                "date", "2023-10-01",
                "detail", "1",
                "key", "baaaa4a6ebd7f03d1c95541b3720a906"
        );
        String birthUrl = "http://v.juhe.cn/calendar/day";
        Map<String, String> birthParm = Map.of(
                "date", "2002-2-11",
                "key", "9b5336bc498abd9b62542fbfb0eca843"
        );
        String locUrl = "https://restapi.amap.com/v3/geocode/geo";
        Map<String, String> locParm = Map.of(
                "address", "北京市",
                "key", "1137f7d2f7a524537f4bd8f10c87ac2d"
        );
        String weatherUrl = "https://restapi.amap.com/v3/weather/weatherInfo";
        Map<String, String> weatherParm = Map.of(
                "city", "110101",
                "key", "59f1ef72f76e3ebd34e5fa1221cf3085"
        );
        APIurlGenerator todayGen = new APIurlGenerator(todayUrl, todayParm, "today");
        APIurlGenerator birthGen = new APIurlGenerator(birthUrl, birthParm, "birthday");
        APIurlGenerator locGen = new APIurlGenerator(locUrl, locParm, "location");
        APIurlGenerator weatherGen = new APIurlGenerator(weatherUrl, weatherParm, "weather");

        Api weatherApi = weatherGen.getExpApi();
        Api locationApi = locGen.getExpApi();
        Api todayApi = todayGen.getExpApi();
        Api birthdayApi = birthGen.getExpApi();

        // Invoke Api and get Response
        ApiInvoker locationApiInvoker = new LocationApiInvoker(app.apiManager);
        ApiInvoker todayApiInvoker = new TodayAnalysisInvoker(app.apiManager);
        ApiInvoker birthdayApiInvoker = new BirthdayApiInvoker(app.apiManager);
        ApiInvoker weatherApiInvoker = new WeatherApiInvoker(app.apiManager);
//        // Register WeatherApi
//        Api weatherApi = app.ApiSetUp(++totalApiCount,"http://t.weather.itboy.net/api/weather/city/101250601", "weather");
//        app.apiManager.registerApi(weatherApi);
//
//        // Register LocationApi
//        Api locationApi = app.ApiSetUp(++totalApiCount, "https://restapi.amap.com/v3/geocode/geo?address=北京市&key=1137f7d2f7a524537f4bd8f10c87ac2d", "location");
//        app.apiManager.registerApi(locationApi);
//        // Invoke LocationApi and get Response
//        ApiInvoker locationApiInvoker = new LocationApiInvoker(app.apiManager);
//
//        // Register TodayApi
//        Api todayApi = app.ApiSetUp(++totalApiCount, "http://apis.juhe.cn/fapig/calendar/day?date=2023-10-01&detail=1&key=baaaa4a6ebd7f03d1c95541b3720a906", "today");
//        app.apiManager.registerApi(todayApi);
//        // Invoke TodayApi and get Response
//        ApiInvoker todayApiInvoker = new TodayAnalysisInvoker(app.apiManager);
//
//
//        // Register BirthdayApi
//        Api birthdayApi = app.ApiSetUp(++totalApiCount, "http://v.juhe.cn/calendar/day?date=2002-2-11&key=9b5336bc498abd9b62542fbfb0eca843", "birthday");
//        app.apiManager.registerApi(birthdayApi);
//        // Invoke BirthdayApi and get Response
//        ApiInvoker birthdayApiInvoker = new BirthdayApiInvoker(app.apiManager);



        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        ApiRequest apiRequest_weather = new ApiRequest();
        apiRequest_weather.setApi(weatherApi);
        apiRequest_weather.setApiUser(new ApiUser("Tom", "123456", Arrays.asList("1", "2", "3")));
        apiRequest_weather.setTimestamp(timestamp);
        apiRequest_weather.setStatus(0);

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

        ApiResponse apiResponse_weather = weatherApiInvoker.invokeApi(apiRequest_weather, logger);
        ApiResponse apiResponse_loc = locationApiInvoker.invokeApi(apiRequest_loc, logger);
        ApiResponse apiResponse_today = todayApiInvoker.invokeApi(apiRequest_today, logger);
        ApiResponse apiResponse_birth = birthdayApiInvoker.invokeApi(apiRequest_birth, logger);
        System.out.println(apiResponse_weather.getResponseBody());
        System.out.println(apiResponse_loc.getResponseBody());
        System.out.println(apiResponse_today.getResponseBody());
        System.out.println(apiResponse_birth.getResponseBody());


    }
}