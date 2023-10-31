package com.company;

import java.util.HashMap;
import java.util.Map;

public interface ApiManager {
    Map<String, Api> apiMap = new HashMap<>();

    void registerApi(Api api);
    Api getApi(String serviceCode);
    void updateApi(String serviceCode, Api newApi);
    void deleteApi(String serviceCode);
}


