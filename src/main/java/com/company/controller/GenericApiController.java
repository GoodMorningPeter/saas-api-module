package com.company.controller;

import com.company.api.GenericApiInvoker;
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
public class GenericApiController {

    private final GenericApiInvoker genericApiInvoker;
    private final ApiLogger apiLogger;

    public GenericApiController(GenericApiInvoker genericApiInvoker, ApiLogger apiLogger) {
        this.genericApiInvoker = genericApiInvoker;
        this.apiLogger = apiLogger;
    }

    @RequestMapping(value = "/generic-api", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> invokeApi(@RequestBody ApiRequest apiRequest) {
        try {
            ApiResponse response = genericApiInvoker.invokeApi(apiRequest, apiLogger);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}