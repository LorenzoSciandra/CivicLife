package com.civiclife.apigateway.service;

import com.civiclife.apigateway.utils.ValidateCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthThread extends Thread{

    private static final String oauth = "http://localhost:8080/authAPI/v1/validate/";

    private String completeLink;
    private ValidateCode validateCode = null;

    public void setCompleteLink(String completeLink) {
        this.completeLink = completeLink;
    }

    private RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        return new RestTemplate(requestFactory);
    }

    @Override
    public void run() {
        validateCode =   restTemplate().getForObject(oauth + completeLink, ValidateCode.class);
    }

    public ValidateCode getValidateCode(){
        return validateCode;
    }

}