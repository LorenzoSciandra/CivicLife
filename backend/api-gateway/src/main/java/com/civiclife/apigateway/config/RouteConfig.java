package com.civiclife.apigateway.config;

import com.civiclife.apigateway.component.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class RouteConfig {

    @LoadBalanced
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/bonusAPI/**", "/vaccinationAPI/**")
                        .filters(f -> f.filter(new AuthFilter()))
                        .uri("lb://EXTERNAL-RESOURCES-SERVICE"))
                .route(r -> r.path("/initiativeAPI/v1/getAllNamesDesc")
                        .uri("lb://INITIATIVE-SERVICE"))
                .route(r -> r.path("/initiativeAPI/**")
                        .filters(f -> f.filter(new AuthFilter()))
                        .uri("lb://INITIATIVE-SERVICE"))
                .route(r-> r.path("/userAPI/**")
                        .filters(f -> f.filter(new AuthFilter()))
                            .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/votationAPI/v1/votation/vote/**",
                                "/votationAPI/v1/votation/updateStatus/**",
                                "/votationAPI/v1/votations/done/**",
                                "/votationAPI/v1/votations/programmed/**")
                        .filters(f -> f.filter(new AuthFilter()))
                        .uri("lb://VOTE-SERVICE"))
                .route(r -> r.path("/candidateAPI/**", "/partyAPI/**",  "/votationAPI/**")
                        .uri("lb://VOTE-SERVICE"))
                .route(r -> r.path("/authAPI/**", "/login", "/login/**", "/oauth2/**")
                        .uri("lb://OAUTH-SERVICE"))
                .build();
    }
}