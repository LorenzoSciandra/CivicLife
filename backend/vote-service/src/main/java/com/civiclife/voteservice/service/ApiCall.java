package com.civiclife.voteservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCall {

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_API = "http://user-service/userAPI/v1/user/isAdmin/";

    public boolean isAdmin(String userId) {
        return Boolean.TRUE.equals(restTemplate.getForObject(USER_API + userId, Boolean.class));
    }

}
