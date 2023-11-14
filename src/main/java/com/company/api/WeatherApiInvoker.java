package com.company.api;

import com.company.ApiError;
import com.company.entity.Api;
import com.company.entity.ApiUser;
import com.company.service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;

public class WeatherApiInvoker implements ApiInvoker {
    private ApiManager apiManager;

    public WeatherApiInvoker(ApiManager apiManager) {
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
            while((str = br.readLine()) != null){
                String regex="\\p{Punct}+";
                String digit[]=str.split(regex);
                response.append("城市:"+digit[22]+digit[18]+"\n");
                response.append("时间:"+digit[49]+"年"+digit[50]+"月"+digit[51]+"日"+digit[53]+"\n");
                response.append("温度:"+digit[47]+"~"+digit[45]+"\n");
                response.append("天气:"+digit[67]+" "+digit[63]+digit[65]+"\n");
                response.append(digit[69]+"\n");
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
