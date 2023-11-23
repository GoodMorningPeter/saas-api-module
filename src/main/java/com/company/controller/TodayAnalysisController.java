package com.company.controller;

import com.company.api.TodayAnalysisInvoker;
import com.company.service.ApiLogger;
import com.company.service.ApiRequest;
import com.company.service.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TodayAnalysisController {

    private final TodayAnalysisInvoker todayAnalysisInvoker;
    private final ApiLogger apiLogger;

    public TodayAnalysisController(TodayAnalysisInvoker todayAnalysisInvoker, ApiLogger apiLogger) {
        this.todayAnalysisInvoker = todayAnalysisInvoker;
        this.apiLogger = apiLogger;
    }

    @RequestMapping(value = "/today-analysis", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> invokeApi(@RequestBody ApiRequest apiRequest) {
        try {
            ApiResponse response = todayAnalysisInvoker.invokeApi(apiRequest, apiLogger);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}