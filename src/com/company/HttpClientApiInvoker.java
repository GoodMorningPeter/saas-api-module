package com.company;

import com.company.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApiInvoker implements ApiInvoker {
    private HttpClient httpClient = HttpClient.newHttpClient();
    private ApiManager apiManager;

    public HttpClientApiInvoker(ApiManager apiManager, ApiErrorHandler errorHandler) {
        this.apiManager = apiManager;
    }

    @Override
    public boolean validateUser(ApiRequest apiRequest) {
        return false;
    }

    public ApiResponse invokeApi(ApiRequest apiRequest) throws IOException, InterruptedException {
        Api apiDefinition = apiManager.getApi(apiRequest.getServiceCode());
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiDefinition.getApiUrl()))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setServiceCode(apiRequest.getServiceCode());
        apiResponse.setStatusCode(httpResponse.statusCode());
        apiResponse.setResponseBody(httpResponse.body());
        return apiResponse;
    }
}