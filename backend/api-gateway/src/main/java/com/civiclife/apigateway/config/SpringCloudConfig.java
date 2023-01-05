package com.civiclife.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/bonusAPI/**", "/vaccinationAPI/**")
                        .uri("lb://EXTERNAL-RESOURCES-SERVICE"))
                .route(r -> r.path("/initiativeAPI/**")
                        .uri("lb://INITIATIVE-SERVICE"))
                .route(r -> r.path("/userAPI/**")
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/candidateAPI/**", "/partyAPI/**", "/resultAPI/**", "/votationAPI/**")
                        .uri("lb://VOTE-SERVICE"))
                .route(r -> r.path("/authAPI/**")
                        .uri("lb://AUTH-SERVICE"))
                .route(r -> r.path("/login").
                        filters(f -> f.setPath("/authAPI/v1/getToken"))
                        .uri("lb://AUTH-SERVICE/"))
                .build();
    }
}