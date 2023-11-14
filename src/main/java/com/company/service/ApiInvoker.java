package com.company.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
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
