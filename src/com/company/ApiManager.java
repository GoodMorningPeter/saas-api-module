package com.company;

import java.util.HashMap;
import java.util.Map;

public class ApiManager {
    private Map<String, Api> apiMap = new HashMap<>();

    public void register(Api api) {
        apiMap.put(api.getServiceCode(), api);
    }

    public Api getApi(String serviceCode) {
        return apiMap.get(serviceCode);
    }

    public void updateApi(String serviceCode, Api newApi) {
        apiMap.put(serviceCode, newApi);
    }

    public void deleteApi(String serviceCode) {
        apiMap.remove(serviceCode);
    }
}
