package com.company.api;

import com.alibaba.fastjson.JSONObject;
import com.company.ApiError;
import com.company.entity.Api;
import com.company.entity.ApiUser;
import com.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherApiInvoker implements ApiInvoker {
//    @Autowired
//    private ApiManager apiManager;
    private final ApiManager apiManager;

    public WeatherApiInvoker(ApiManager apiManager) {
        this.apiManager = apiManager;
    }

    @Override
    public boolean validateUser(ApiRequest apiRequest) {
        // 在这里添加用户验证和权限检查逻辑
        // 如果用户验证通过并且用户有权限调用API，返回true
        // 否则，返回false
        ApiUser apiUser = apiRequest.getApiUser();
        Api api = apiRequest.getApi();

        if (apiUser == null || api == null) {
            return false;
        }

        List<String> userPermissions = apiUser.getPermissions();
        List<String> requiredPermissions = api.getRequiredPermissions();

        if (userPermissions == null || requiredPermissions == null) {
            return false;
        }

        // 检查用户是否拥有所有需要的权限
        return userPermissions.containsAll(requiredPermissions);
    }

    public ApiResponse invokeApi(ApiRequest apiRequest, ApiLogger logger) throws IOException {
        if (!validateUser(apiRequest)) {
            // 如果用户没有权限，返回一个表示权限错误的ApiResponse
            return new ApiResponse("403");
        }

        long start = System.currentTimeMillis();
        ApiResponse apiResponse = new ApiResponse();
        String errorMessage = null;
        try {
            Api apiDefinition = apiManager.getApiByDescription(apiRequest.getApi().getApiDescription());
            URL url = new URL(apiDefinition.getApiUrl());
            InputStreamReader isReader = new InputStreamReader(url.openStream(), "UTF-8");
            BufferedReader br = new BufferedReader(isReader);
            String str;
            StringBuilder response = new StringBuilder();
            String weather = "";
            String temperature = "";
            String winddirection = "";
            String windpower = "";
            String humidity = "";
            while((str = br.readLine()) != null){
                JSONObject obj_all = JSONObject.parseObject(str);
                String obj_a = obj_all.get("lives").toString();
                JSONObject obj = JSONObject.parseObject(obj_a.substring(1, obj_a.length()-1));
                weather = String.valueOf(obj.get("weather"));
                temperature = String.valueOf(obj.get("temperature"));
                winddirection = String.valueOf(obj.get("winddirection"));
                windpower = String.valueOf(obj.get("windpower"));
                humidity = String.valueOf(obj.get("humidity"));

                response.append("天气：" + weather + "\n");
                response.append("温度：" + temperature + "\n");
                response.append("风向：" + winddirection + "\n");
                response.append("风力：" + windpower + "\n");
                response.append("湿度：" + humidity + "\n");
            }
            br.close();
            isReader.close();
            apiResponse.setResponseBody(response.toString());
        }catch (Exception exp) {
//            throw exp;
            ApiError error = new ApiError();
            error.setMessage(exp.getMessage());
            error.setDetails(exp.toString());
            error.setTimestamp(LocalDateTime.now());
            apiResponse.setError(error);
            apiResponse.setResponseBody("404");
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.logApiCall(apiRequest, apiResponse, duration);
            logger.updateApiUsageStats(apiRequest.getApi().getId());
        }
        return apiResponse;
    }
}
