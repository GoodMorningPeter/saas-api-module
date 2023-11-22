package com.company.service;

import java.util.HashMap;
import java.util.Map;
import com.company.entity.Api;

public interface ApiManager {
    Map<String, Api> apiMap = new HashMap<>();

    void registerApi(Api api);
    Api getApiByDescription(String description);
    void updateApi(Api newApi);
    void deleteApi(String description);
}


