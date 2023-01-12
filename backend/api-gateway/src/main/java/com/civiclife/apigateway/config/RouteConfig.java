package com.civiclife.apigateway.config;

import com.civiclife.apigateway.component.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class RouteConfig {

    private AuthFilter authFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/bonusAPI/v1/bonuses/**", "/vaccinationAPI/v1/vaccinations/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://EXTERNAL-RESOURCES-SERVICE"))
                .route(r -> r.path("/initiativeAPI/**")
                        .uri("lb://INITIATIVE-SERVICE"))
                .route(r -> r.path("/userAPI/v1/postman").uri("lb://USER-SERVICE"))
                .route(r-> r.path("/userAPI/**")
                        .filters(f -> f.filter(authFilter, -1))
                            .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/candidateAPI/**", "/partyAPI/**", "/resultAPI/**", "/votationAPI/**")
                        .uri("lb://VOTE-SERVICE"))
                .route(r -> r.path("/authAPI/**", "/login", "/login/**", "/oauth2/**")
                        .uri("lb://OAUTH-SERVICE"))
                .build();
    }
}