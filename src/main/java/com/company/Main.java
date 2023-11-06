package com.company;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    public static Api ApiSetUp(String urlString, String ServiceCode){
        Api weatherApi = new Api();
        weatherApi.setServiceCode(ServiceCode);
        weatherApi.setApiUrl(urlString);
        return weatherApi;
    }


    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/api", "morning", "123456");
//        ApiLogger logger = new DatabaseApiLogger(connection);
        ApiLogger logger = new DatabaseApiLogger(null);

        ApiManager apiManager = new MemoryApiManager();

        // Register WeatherApi
        Api weatherApi = ApiSetUp("http://t.weather.itboy.net/api/weather/city/101250601", "weather");
        apiManager.registerApi(weatherApi);
        // Invoke WeatherAPi and get Response
        ApiInvoker weatherApiInvoker = new WeatherApiInvoker(apiManager);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setServiceCode("weather");
        ApiResponse apiResponse = weatherApiInvoker.invokeApi(apiRequest, logger);
        System.out.println(apiResponse.getResponseBody());

        // Register LocationApi
    }
}
