package com.company.controller;

import com.company.api.WeatherApiInvoker;
import com.company.service.ApiLogger;
import com.company.service.ApiRequest;
import com.company.service.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class WeatherApiController {

    private final WeatherApiInvoker weatherApiInvoker;
    private final ApiLogger apiLogger;

    public WeatherApiController(WeatherApiInvoker weatherApiInvoker, ApiLogger apiLogger) {
        this.weatherApiInvoker = weatherApiInvoker;
        this.apiLogger = apiLogger;
    }

    @RequestMapping(value = "/weather-api", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> invokeApi(@RequestBody ApiRequest apiRequest) {
        try {
            ApiResponse response = weatherApiInvoker.invokeApi(apiRequest, apiLogger);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}