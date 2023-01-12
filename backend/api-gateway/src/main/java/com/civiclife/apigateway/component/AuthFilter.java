package com.civiclife.apigateway.component;

import com.civiclife.apigateway.config.ValidateCode;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Order(-1)
@Component
public class AuthFilter implements GatewayFilter {

    private final String AUTH_URL = "http://localhost:8080/authAPI/v1/validate/";
    private final String ERROR_URL = "http://localhost:3000/error?errorReason=";

    public enum OauthProvider {
        Google,
        Facebook,
        Github
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String path = exchange.getRequest().getURI().toString();
            System.out.println("########################################################");
            System.out.println("Url iniziale: " + path );
            System.out.println("Params: "+ exchange.getRequest().getQueryParams().toString());

            MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
            String final_url = "";

            String token = new String(Base64.getDecoder().decode(params.get("token").get(0)));
            String providerString = new String(Base64.getDecoder().decode(params.get("provider").get(0)));
            String email = new String(Base64.getDecoder().decode(params.get("email").get(0)));
            OauthProvider provider = OauthProvider.valueOf(providerString);

            if(!email.equals("") && !token.equals("") && !providerString.equals("")){
                String url = path.split("\\?")[0];
                System.out.println("I find: " + email + " " + token + " " + provider);

                String validateUrl = AUTH_URL + email + "/" + token + "/" + provider;
                ValidateCode validateCode =  new RestTemplate().getForObject(validateUrl, ValidateCode.class);

                System.out.println(validateCode);

                if(validateCode == ValidateCode.ACTIVE){
                    final_url = url + "/" + email;
                }
                else{
                    assert validateCode != null;
                    final_url = ERROR_URL +  validateCode.toString();
                }

            }

            else{
                final_url = ERROR_URL + "ValuesToAuthenticateNotFound";
            }

            exchange.transformUrl(final_url);
            ServerHttpRequest request = exchange.getRequest().mutate().uri(URI.create(final_url)).build();
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());

            System.out.println("-------------------------------------------------------");

            System.out.println("Final url: " + final_url);
            System.out.println("Headers: "+ exchange.getRequest().getHeaders().toString());
            System.out.println("#####################################################");


            return chain.filter(exchange.mutate().request(request).build());


        } catch (NullPointerException e) {
            exchange.getResponse().getHeaders().set("status", "401");
            return Mono.empty();
        }

    }

}
