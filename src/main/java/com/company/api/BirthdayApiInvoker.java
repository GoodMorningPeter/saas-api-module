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

public class BirthdayApiInvoker implements ApiInvoker{
    private final ApiManager apiManager;

    public BirthdayApiInvoker(ApiManager apiManager) {
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
            Api apiDefinition = apiManager.getApiByDescription(apiRequest.getApi().getApiDescription());
            URL url = new URL(apiDefinition.getApiUrl());
            InputStreamReader isReader =  new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isReader);
            String str;
            StringBuilder response = new StringBuilder();

            String animalsYear = "";
            String lunarYear = "";
            String lunar = "";
            String weekday = "";
            while((str = br.readLine()) != null){
                JSONObject obj_all = JSONObject.parseObject(str);
                JSONObject obj_res = JSONObject.parseObject(obj_all.get("result").toString());
                JSONObject obj = JSONObject.parseObject(obj_res.get("data").toString());
                animalsYear = String.valueOf(obj.get("animalsYear"));
                lunarYear = String.valueOf(obj.get("lunarYear"));
                lunar = String.valueOf(obj.get("lunar"));
                weekday = String.valueOf(obj.get("weekday"));

                response.append("生肖：" + animalsYear + "\n");
                response.append("农历：" + lunarYear + lunar + "\n");
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

