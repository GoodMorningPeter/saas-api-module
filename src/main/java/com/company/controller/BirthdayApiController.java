package com.company.controller;

import com.company.api.BirthdayApiInvoker;
import com.company.service.ApiLogger;
import com.company.service.ApiRequest;
import com.company.service.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BirthdayApiController {

    private final BirthdayApiInvoker birthdayApiInvoker;
    private final ApiLogger apiLogger;

    public BirthdayApiController(BirthdayApiInvoker birthdayApiInvoker, ApiLogger apiLogger) {
        this.birthdayApiInvoker = birthdayApiInvoker;
        this.apiLogger = apiLogger;
    }

    @RequestMapping(value = "/birthday-api", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> invokeApi(@RequestBody ApiRequest apiRequest) {
        try {
            ApiResponse response = birthdayApiInvoker.invokeApi(apiRequest, apiLogger);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}