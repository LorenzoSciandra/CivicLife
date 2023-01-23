package com.civiclife.externalresourcesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableDiscoveryClient
@EnableMongoRepositories
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ExternalResourcesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalResourcesServiceApplication.class, args);
    }

}
