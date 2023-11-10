package com.company.controller;

import com.company.entity.CallLog;
import com.company.entity.UsageStats;
import com.company.service.CallLogService;
import com.company.service.UsageStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private CallLogService callLogService;
    @Autowired
    private UsageStatsService usageStatsService;


    @GetMapping("/apiInfo")
    public List<UsageStats> getApiInfo(String apiId){
        List<UsageStats> all = usageStatsService.findAll();
        List<UsageStats> collect = all.stream().filter(log -> log.getApi_id().equals(apiId)).collect(Collectors.toList());
        return collect;
    }


    @GetMapping("/apiDetail")
    public List<CallLog> getApiDetail(String apiId){
        List<CallLog> all = callLogService.findAll();
        List<CallLog> collect = all.stream().filter(log -> log.getApi_id().equals(apiId)).collect(Collectors.toList());
        return collect;
    }

}
