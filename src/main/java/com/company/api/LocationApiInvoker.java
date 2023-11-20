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
import java.util.Map;

public class LocationApiInvoker implements ApiInvoker{
    private ApiManager apiManager;
    private static String SUCCESS_FLAG="1";

    public LocationApiInvoker(ApiManager apiManager) {
        this.apiManager = apiManager;
    }

    @Override
    public boolean validateUser(ApiRequest apiRequest) {
        ApiUser apiUser = apiRequest.getApiUser();
        // 添加用户验证和权限检查逻辑
        // 如果用户验证通过并且用户有权限调用API，返回true
        // 否则，返回false
        return true;
    }

    public ApiResponse invokeApi(ApiRequest apiRequest, ApiLogger logger) {
        long start = System.currentTimeMillis();
        ApiResponse apiResponse = new ApiResponse();
        String errorMessage = null;
        try{
            Api apiDefinition = apiManager.getApi(apiRequest.getApi().getId());
            URL url = new URL(apiDefinition.getApiUrl());
            InputStreamReader isReader =  new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isReader);
            String str;
            StringBuilder response = new StringBuilder();
            String location="";
            while((str = br.readLine()) != null){
                JSONObject obj = JSONObject.parseObject(str);
                if(String.valueOf(obj.get("status")).equals(SUCCESS_FLAG)){
                    JSONObject jobJSON = JSONObject.parseObject(obj.get("geocodes").toString().substring(1, obj.get("geocodes").toString().length() - 1));
                    location = String.valueOf(jobJSON.get("location"));
                }else{
                    throw new RuntimeException("地址转换经纬度失败，错误码：" + obj.get("infocode"));
                }
                response.append("经纬度：" + location + "\n");
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
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.logApiCall(apiRequest, apiResponse, duration);
            logger.updateApiUsageStats(apiRequest.getApi().getId());
        }
        return apiResponse;
    }
}
