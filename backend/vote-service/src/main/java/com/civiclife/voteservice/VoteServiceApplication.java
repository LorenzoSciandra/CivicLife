package com.civiclife.voteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteServiceApplication.class, args);
        System.out.println("VoteServiceApplication started");
    }

}
