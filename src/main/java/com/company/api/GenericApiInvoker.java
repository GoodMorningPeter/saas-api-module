package com.company.api;

import com.company.entity.Api;
import com.company.entity.ApiUser;
import com.company.service.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class GenericApiInvoker implements ApiInvoker {
    private final ApiManager apiManager;

    public GenericApiInvoker(ApiManager apiManager) {
        this.apiManager = apiManager;
    }

    @Override
    public boolean validateUser(ApiRequest apiRequest) {
        ApiUser apiUser = apiRequest.getApiUser();
        // 在这里添加用户验证和权限检查逻辑
        // 如果用户验证通过并且用户有权限调用API，返回true
        // 否则，返回false
        return true;
    }

    @Override
    public ApiResponse invokeApi(ApiRequest apiRequest, ApiLogger logger) throws IOException {
        long start = System.currentTimeMillis();
        ApiResponse apiResponse = new ApiResponse();

        try {
            Api apiDefinition = apiManager.getApiByDescription(apiRequest.getApi().getApiDescription());
            URL url = new URL(apiDefinition.getApiUrl());

            try (InputStreamReader isReader = new InputStreamReader(url.openStream(), "UTF-8");
                 BufferedReader br = new BufferedReader(isReader)) {

                StringBuilder response = new StringBuilder();
                String str;

                while ((str = br.readLine()) != null) {
                    response.append(str);
                }

                apiResponse.setResponseBody(response.toString());
            }
        } catch (Exception exp) {
            throw exp;
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.logApiCall(apiRequest, apiResponse, duration);
            logger.updateApiUsageStats(apiRequest.getApi().getId());
        }

        return apiResponse;
    }
}