package com.civiclife.initiativeservice.service;

import com.civiclife.initiativeservice.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCall {

    @Autowired
    private RestTemplate restTemplate;

    private static final String userStatusAPI = "http://user-service/userAPI/v1/user/getStatus/";

    public UserStatus getUserStatus(String userId) {
       return restTemplate.getForObject(userStatusAPI + userId, UserStatus.class);
    }

}
