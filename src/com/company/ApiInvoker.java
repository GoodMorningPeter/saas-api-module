package com.company;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public interface ApiInvoker {
//    private HttpClient httpClient = HttpClient.newHttpClient();
//
//    public ApiResponse invoke(Api api) throws IOException, InterruptedException, URISyntaxException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(new URI(api.getServiceCode()))
//                .header("appKey", api.getAppKey())
//                .header("requestTime", api.getRequestTime())
//                .header("sign", api.getSign())
//                .method("POST", HttpRequest.BodyPublishers.ofString(api.getData()))
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // parse the response body to ApiResponse
//        JSONObject json = new JSONObject(response.body());
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setExecuteResult(json.getString("executeResult"));
//        apiResponse.setErrorCode(json.getString("errorCode"));
//        apiResponse.setErrorMessage(json.getString("errorMessage"));
//        apiResponse.setData(json.getString("data"));
//
//        return apiResponse;
//    }

    boolean validateUser(ApiRequest apiRequest);
    ApiResponse invokeApi(ApiRequest apiRequest, ApiLogger logger) throws IOException, InterruptedException;
}
