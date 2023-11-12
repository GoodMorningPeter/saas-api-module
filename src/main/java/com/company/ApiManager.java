package com.company;

import java.util.HashMap;
import java.util.Map;
import com.company.entity.Api;

public interface ApiManager {
    Map<Integer, Api> apiMap = new HashMap<>();

    void registerApi(Api api);
    Api getApi(Integer id);
    void updateApi(Integer id, Api newApi);
    void deleteApi(Integer id);
}


