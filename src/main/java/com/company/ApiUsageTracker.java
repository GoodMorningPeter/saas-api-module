package com.company;

import java.util.HashMap;
import java.util.Map;

public class ApiUsageTracker {
    private Map<String, Integer> usageMap = new HashMap<>();

    public void track(String serviceCode) {
        usageMap.put(serviceCode, usageMap.getOrDefault(serviceCode, 0) + 1);
    }

    public int getUsage(String serviceCode) {
        return usageMap.getOrDefault(serviceCode, 0);
    }

    public Map<String, Integer> getAllUsages() {
        return new HashMap<>(usageMap);
    }
}
