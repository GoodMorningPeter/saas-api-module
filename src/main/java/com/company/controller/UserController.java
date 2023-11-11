package com.company.controller;

import com.company.entity.Api;
import com.company.entity.CallLog;
import com.company.entity.UsageStats;
import com.company.service.ApiService;
import com.company.service.CallLogService;
import com.company.service.UsageStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private CallLogService callLogService;
    @Autowired
    private UsageStatsService usageStatsService;
    @Autowired
    private ApiService apiService;


    @GetMapping("/apiInfo")
    public List<UsageStats> getApiInfo(Integer id){
        List<UsageStats> all = usageStatsService.findAll();
        List<UsageStats> collect = all.stream().filter(log -> log.getId().equals(id)).collect(Collectors.toList());
        return collect;
    }


    @GetMapping("/apiDetail")
    public List<CallLog> getApiDetail(Integer id){
        List<CallLog> all = callLogService.findAll();
        List<CallLog> collect = all.stream().filter(log -> log.getId().equals(id)).collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/apiAll")
    public List<Api> findAll() {
        return apiService.findAll();
    }

    @PostMapping("/apiAdd")
    public String addApi(@RequestBody Api api) {
        int result = apiService.addApi(api);
        return result == 1 ? "API added successfully" : "Error adding API";
    }

    @PutMapping("/apiUpdate")
    public String updateApi(@RequestBody Api api) {
        int result = apiService.updateApi(api);
        return result == 1 ? "API updated successfully" : "Error updating API";
    }

    @DeleteMapping("/apiDelete/{id}")
    public String deleteApi(@PathVariable Integer id) {
        int result = apiService.deleteApi(id);
        return result == 1 ? "API deleted successfully" : "Error deleting API";
    }
}
