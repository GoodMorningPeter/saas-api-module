package com.company;

import java.io.IOException;


public class Main {
    public static Api ApiSetUp(String urlString, String ServiceCode){
        Api weatherApi = new Api();
        weatherApi.setServiceCode(ServiceCode);
        weatherApi.setApiUrl(urlString);
        return weatherApi;
    }


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

        // Register WeatherApi
        Api weatherApi = ApiSetUp("http://t.weather.itboy.net/api/weather/city/101250601", "weather");
        apiManager.registerApi(weatherApi);
        // Invoke WeatherAPi and get Response
        ApiInvoker weatherApiInvoker = new WeatherApiInvoker(apiManager);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setServiceCode("weather");
        ApiResponse apiResponse = weatherApiInvoker.invokeApi(apiRequest);
        System.out.println(apiResponse.getResponseBody());

        // Register LocationApi
    }
}
