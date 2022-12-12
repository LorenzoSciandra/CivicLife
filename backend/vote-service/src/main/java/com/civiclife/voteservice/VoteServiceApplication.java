package com.civiclife.voteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableMongoRepositories
public class VoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteServiceApplication.class, args);
        System.out.println("VoteServiceApplication started");
    }

}
