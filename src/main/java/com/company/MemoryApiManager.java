package com.company;

import com.company.entity.Api;

import java.util.HashMap;
import java.util.Map;

public class MemoryApiManager implements ApiManager {
    private Map<Integer, Api> apiMap = new HashMap<>();

    public void registerApi(Api api) {
        apiMap.put(api.getId(), api);
    }

    public void updateApi(Integer apiId, Api api) {
        apiMap.put(apiId, api);
    }

    public void deleteApi(Integer apiId) {
        apiMap.remove(apiId);
    }

    public Api getApi(Integer apiId) {
        return apiMap.get(apiId);
    }
}