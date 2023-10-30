package com.company;

import com.company.Api;
import com.company.ApiManager;
import java.util.HashMap;
import java.util.Map;

public class MemoryApiManager implements ApiManager {
    private Map<String, Api> apiMap = new HashMap<>();

    public void registApi(Api api) {
        apiMap.put(api.getServiceCode(), api);
    }

    public void updateApi(String apiId, Api api) {
        apiMap.put(apiId, api);
    }

    public void deleteApi(String apiId) {
        apiMap.remove(apiId);
    }

    public Api getApi(String apiId) {
        return apiMap.get(apiId);
    }
}