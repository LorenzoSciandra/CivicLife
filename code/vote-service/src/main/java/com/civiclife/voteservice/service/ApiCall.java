package com.civiclife.voteservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCall {

    @Autowired
    private RestTemplate restTemplate;

    public boolean isAdmin(String userId) {
        String USER_API = "http://user-service/userAPI/v1/user/isAdmin/";
        String finalUrl = USER_API + userId + "/"+ userId;
        return Boolean.TRUE.equals(restTemplate.getForObject(finalUrl, Boolean.class));
    }

}
