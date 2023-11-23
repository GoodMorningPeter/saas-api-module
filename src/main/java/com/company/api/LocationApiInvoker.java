package com.company.api;
import com.company.ApiError;
import com.company.entity.Api;
import com.company.entity.ApiUser;
import com.company.service.*;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationApiInvoker implements ApiInvoker{

    private final ApiManager apiManager;
    private static String SUCCESS_FLAG="1";

    public LocationApiInvoker(ApiManager apiManager) {
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

    public ApiResponse invokeApi(ApiRequest apiRequest, ApiLogger logger) {
        if (!validateUser(apiRequest)) {
            // 如果用户没有权限，返回一个表示权限错误的ApiResponse
            return new ApiResponse("403");
        }
        long start = System.currentTimeMillis();
        ApiResponse apiResponse = new ApiResponse();
        String errorMessage = null;
        try{
            Api apiDefinition = apiManager.getApiByDescription(apiRequest.getApi().getApiDescription());
            URL url = new URL(apiDefinition.getApiUrl());
            InputStreamReader isReader =  new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isReader);
            String str;
            StringBuilder response = new StringBuilder();
            String location="";
            String country = "";
            String city = "";
            String adcode = "";
            while((str = br.readLine()) != null){
                JSONObject obj = JSONObject.parseObject(str);
                if(String.valueOf(obj.get("status")).equals(SUCCESS_FLAG)){
                    JSONObject jobJSON = JSONObject.parseObject(obj.get("geocodes").toString().substring(1, obj.get("geocodes").toString().length() - 1));
                    location = String.valueOf(jobJSON.get("location"));
                    country = String.valueOf(jobJSON.get("country"));
                    city = String.valueOf(jobJSON.get("city"));
                    adcode = String.valueOf(jobJSON.get("adcode"));
                }else{
                    throw new RuntimeException("地址转换经纬度失败，错误码：" + obj.get("infocode"));
                }

                response.append("经纬度：" + location + "\n");
                response.append("国家：" + country + "\n");
                response.append("城市：" + city + "\n");
                response.append("邮编：" + adcode + "\n");
            }
            br.close();
            isReader.close();

            apiResponse.setResponseBody(response.toString());
        } catch (Exception exp) {
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
