package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class WeatherApiInvoker implements ApiInvoker {
    private ApiManager apiManager;

    public WeatherApiInvoker(ApiManager apiManager) {
        this.apiManager = apiManager;
    }

    public ApiResponse invokeApi(ApiRequest apiRequest) {
        ApiResponse apiResponse = new ApiResponse();
        try{
            Api apiDefinition = apiManager.getApi(apiRequest.getServiceCode());
            URL url = new URL(apiDefinition.getApiUrl());
            InputStreamReader isReader =  new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isReader);
            String str;
            StringBuilder response = new StringBuilder();
            while((str = br.readLine()) != null){
                String regex="\\p{Punct}+";
                String digit[]=str.split(regex);
                response.append("城市:"+digit[22]+digit[18]+"\n");
                response.append("时间:"+digit[49]+"年"+digit[50]+"月"+digit[51]+"日"+digit[53]+"\n");
                response.append("温度:"+digit[47]+"~"+digit[45]+"\n");
                response.append("天气:"+digit[67]+" "+digit[63]+digit[65]+"\n");
                response.append(digit[69]+"\n");
            }
            br.close();
            isReader.close();

            apiResponse.setResponseBody(response.toString());
        }
        catch(Exception exp){
            ApiError apiError = new ApiError();
            apiError.setServiceCode(apiRequest.getServiceCode());
            apiError.setErrorMessage(exp.getMessage());
            apiResponse.setApiError(apiError);
        }
        return apiResponse;
    }
}
