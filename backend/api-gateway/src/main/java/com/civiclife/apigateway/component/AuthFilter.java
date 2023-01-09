package com.civiclife.apigateway.component;

import com.civiclife.apigateway.config.ValidateCode;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.sql.SQLOutput;
import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Order(-1)
@Component
public class AuthFilter implements GatewayFilter {

    private final String AUTH_URL = "http://localhost:8080/authAPI/v1/validate/";

    public enum OauthProvider {
        Google,
        Facebook,
        Github
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {

            System.out.println(exchange.getRequest().getHeaders().toString());
            String email = exchange.getRequest().getHeaders().get("Email").get(0);
            String token = exchange.getRequest().getHeaders().get("Token").get(0);
            OauthProvider provider = OauthProvider.valueOf(exchange.getRequest().getHeaders().get("Provider").get(0));

            if(email != null && token != null) {
                System.out.println("I find: " + email + " " + token + " " + provider);

                String validateUrl = AUTH_URL + email + "/" + token + "/" + provider;
                ValidateCode validateCode =  new RestTemplate().getForObject(validateUrl, ValidateCode.class);

                if(validateCode == ValidateCode.ACTIVE){
                    String url = exchange.getRequest().getPath().toString();
                    int lastIndex = url.lastIndexOf("/");
                    String final_url = url.substring(0,lastIndex) + "/" + email;
                    System.out.println("Final url: " + final_url);
                    exchange.transformUrl(final_url);
                    ServerHttpRequest request = exchange.getRequest().mutate().path(final_url).build();
                    exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());

                    return chain.filter(exchange.mutate().request(request).build());
                }

                System.out.println(validateCode);
            }
            return Mono.empty();

        } catch (NullPointerException e) {
            exchange.getResponse().getHeaders().set("status", "401");
            return Mono.empty();
        }

    }

}
