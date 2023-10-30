package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
	// write your code here
        ApiManager apiManager = new MemoryApiManager();
        ApiErrorHandler errorHandler = new ConsoleApiErrorHandler();
        ApiInvoker apiInvoker = new HttpClientApiInvoker(apiManager, errorHandler);

        Api weatherApi = new Api();
        weatherApi.setServiceCode("1");
        weatherApi.setApiUrl("http://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=San Francisco");
        apiManager.registApi(weatherApi);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setServiceCode("1");
        ApiResponse apiResponse = apiInvoker.invokeApi(apiRequest);
        errorHandler.logApiCall(apiRequest, apiResponse);
    }
}
