package com.company.service;

import com.company.entity.Api;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class MemoryApiManager implements ApiManager {
    public Map<String, Api> apiMap = new HashMap<>();

    public void registerApi(Api api) {
        apiMap.put(api.getApiDescription(), api);
    }

    public void updateApi(Api api) {
        apiMap.put(api.getApiDescription(), api);
    }

    public void deleteApi(String apiDescription) {
        apiMap.remove(apiDescription);
    }

    public Api getApiByDescription(String apiDescription) {
        return apiMap.get(apiDescription);
    }
}