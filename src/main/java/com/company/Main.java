package com.company;

import com.company.entity.Api;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class Main {
    public static Integer totalApiCount = 0;
    public static Api ApiSetUp(Integer id, String urlString, String ApiDescription){
        Api weatherApi = new Api();
        weatherApi.setId(id);
        weatherApi.setApiDescription(ApiDescription);
        weatherApi.setApiUrl(urlString);
        return weatherApi;
    }


    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/api", "morning", "123456");
//        ApiLogger logger = new DatabaseApiLogger(connection);
        ApiLogger logger = new DatabaseApiLogger(null);

        ApiManager apiManager = new MemoryApiManager();

        // Register WeatherApi
        Api weatherApi = ApiSetUp(++totalApiCount,"http://t.weather.itboy.net/api/weather/city/101250601", "weather");
        apiManager.registerApi(weatherApi);
        // Invoke WeatherAPi and get Response
        ApiInvoker weatherApiInvoker = new WeatherApiInvoker(apiManager);

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApi(weatherApi);
        apiRequest.setApiUser(new ApiUser("Tom", "123456"));
        apiRequest.setTimestamp(timestamp);
        apiRequest.setStatus(0);

        ApiResponse apiResponse = weatherApiInvoker.invokeApi(apiRequest, logger);
        System.out.println(apiResponse.getResponseBody());

        // Register LocationApi
    }
}
