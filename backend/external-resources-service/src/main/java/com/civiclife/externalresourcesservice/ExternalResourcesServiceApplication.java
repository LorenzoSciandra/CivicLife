package com.civiclife.externalresourcesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

//@EnableDiscoveryClient
@EnableJpaRepositories
@SpringBootApplication
public class ExternalResourcesServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExternalResourcesServiceApplication.class, args);
    }


}
