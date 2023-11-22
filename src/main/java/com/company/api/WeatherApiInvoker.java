package com.company.api;

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
        try{
            Api apiDefinition = apiManager.getApiByDescription(apiRequest.getApi().getApiDescription());
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
            throw exp;
//            ApiError error = new ApiError();
//            error.setMessage(exp.getMessage());
//            error.setDetails(exp.toString());
//            error.setTimestamp(LocalDateTime.now());
//            apiResponse.setError(error);
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.logApiCall(apiRequest, apiResponse, duration);
            logger.updateApiUsageStats(apiRequest.getApi().getId());
        }
        return apiResponse;
    }
}
