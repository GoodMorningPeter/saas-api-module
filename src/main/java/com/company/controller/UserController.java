package com.company.controller;

import com.company.api.*;
import com.company.entity.Api;
import com.company.entity.ApiUser;
import com.company.entity.CallLog;
import com.company.entity.UsageStats;
import com.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private CallLogService callLogService;
    @Autowired
    private DatabaseApiLogger databaseApiLogger;
    @Autowired
    private UsageStatsService usageStatsService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private MemoryApiManager apiManager;


    @GetMapping("/apiInfo")
    public List<UsageStats> getApiInfo(Integer id){
        List<UsageStats> all = usageStatsService.findAll();
        List<UsageStats> collect = all.stream().filter(log -> log.getId().equals(id)).collect(Collectors.toList());
        return collect;
    }


    @GetMapping("/apiDetail")
    public List<CallLog> getApiDetail(Integer id){
        List<CallLog> all = callLogService.findAll();
//        List<CallLog> all = DatabaseApiLogger.callLogService.findAll();
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
        if(result == 1){
            apiManager.registerApi(api);
        }
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

    @ResponseBody
    @PostMapping("/apiCall")
    public ResponseEntity<Map<String, String>> callApi(@RequestBody Map<String, String> body) {
        try {
            String apiDescription = body.get("description");
            System.out.println("description: " + apiDescription);

            Api api = apiManager.apiMap.get(apiDescription);
            if (api == null) {
                System.out.println("No Api object found for description: " + apiDescription);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "No Api object found for description: " + apiDescription));
            }

            System.out.println("Found Api object: " + api.getApiUrl());

            ApiInvoker apiInvoker = selectApiInvoker(api);
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.setApi(api);
            apiRequest.setApiUser(new ApiUser("Tom", "123456"));
            apiRequest.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            apiRequest.setStatus(0);

            ApiResponse apiResponse = apiInvoker.invokeApi(apiRequest, databaseApiLogger);
            if (apiResponse == null) {
                System.out.println("No ApiResponse returned from invokeApi method");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "No ApiResponse returned from invokeApi method"));
            }

            System.out.println("Got ApiResponse: " + apiResponse.getResponseBody());

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("responseBody", apiResponse.getResponseBody()));
        } catch (Exception e) {
            System.out.println("An error occurred in callApi method");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred: " + e.getMessage()));
        }
    }


    private ApiInvoker selectApiInvoker(Api api) {
        switch (api.getApiDescription()) {
            case "weather":
                return new WeatherApiInvoker(apiManager);
            case "location":
                return new LocationApiInvoker(apiManager);
            case "today":
                return new TodayAnalysisInvoker(apiManager);
            case "birthday":
                return new BirthdayApiInvoker(apiManager);

            // Add more cases as needed

            default:
                // Use GenericApiInvoker for unknown API types
                return new GenericApiInvoker(apiManager);
        }
    }
}
