package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
	// write your code here
//        ApiManager apiManager = new MemoryApiManager();
//        ApiErrorHandler errorHandler = new ConsoleApiErrorHandler();
//        ApiInvoker apiInvoker = new HttpClientApiInvoker(apiManager, errorHandler);
//
//        Api weatherApi = new Api();
//        weatherApi.setServiceCode("weather");
//        weatherApi.setApiUrl("http://t.weather.itboy.net/api/weather/city/101250601");
//        apiManager.registApi(weatherApi);
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.setServiceCode("weather");
//        ApiResponse apiResponse = apiInvoker.invokeApi(apiRequest);
//        errorHandler.logApiCall(apiRequest, apiResponse);

        ApiManager apiManager = new MemoryApiManager();
        Api weatherApi = new Api();
        weatherApi.setServiceCode("weather");
        weatherApi.setApiUrl("http://t.weather.itboy.net/api/weather/city/101250601");
        apiManager.registApi(weatherApi);

        ApiInvoker weatherApiInvoker = new WeatherApiInvoker(apiManager);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setServiceCode("weather");
        ApiResponse apiResponse = weatherApiInvoker.invokeApi(apiRequest);
        System.out.println(apiResponse.getResponseBody());
    }
}
