package com.civiclife.apigateway.component;

import com.civiclife.apigateway.utils.ValidateCode;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.Base64;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Order(-1)
@Component
public class AuthFilter implements GatewayFilter {

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
            System.out.println("Params: "+ exchange.getRequest().getQueryParams());
            System.out.println("Headers: "+ exchange.getRequest().getHeaders());
            System.out.println("-------------------------------------------------------");

            MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
            String final_url = "";
            ValidateCode error = ValidateCode.AUTH_SERVER_ERROR;

            String token = new String(Base64.getDecoder().decode(params.get("token").get(0)));
            String providerString = new String(Base64.getDecoder().decode(params.get("provider").get(0)));
            String email = new String(Base64.getDecoder().decode(params.get("email").get(0)));
            OauthProvider provider = getProvider(providerString);

            if(!email.equals("") && !token.equals("") && provider!=null){
                System.out.println("I find: " + email + " " + token + " " + provider);

                String AUTH_URL = "http://localhost:8080/authAPI/v1/validate/";
                String validateUrl = AUTH_URL + email + "/" + token + "/" + provider;

                AuthThread authThread = new AuthThread(validateUrl);
                authThread.start();
                authThread.join(500);

                if(authThread.isAlive()) {
                    authThread.interrupt();
                }
                else{
                    ValidateCode validateCode =  authThread.getValidateCode();

                    if(validateCode != null){
                        System.out.println(validateCode);

                        if(validateCode == ValidateCode.ACTIVE){
                            final_url = path.split("\\?")[0] + "/" + email;
                        }
                        else{
                            error = validateCode;
                        }
                    }

                }
            }
            else{
                error = ValidateCode.INCOMPLETE_CREDENTIALS;
            }

            if(final_url.equals("")){
                int pos = path.indexOf("v1");
                String vecchioUrl = path.split("\\?")[0].substring(0, pos + 2);
                String errorPath = new String(Base64.getEncoder().encode(path.split("\\?")[0].getBytes()));
                final_url = vecchioUrl + "/error/" + error + "/" + errorPath + "/" + exchange.getRequest().getMethod();
            }

            exchange.transformUrl(final_url);
            ServerHttpRequest request = exchange.getRequest().mutate().uri(URI.create(final_url)).build();
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());

            System.out.println("Final url: " + final_url);
            System.out.println("#####################################################");

            return chain.filter(exchange.mutate().request(request).build());

        } catch (NullPointerException e) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return Mono.empty();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static class AuthThread extends Thread{
        private final String url;
        private ValidateCode validateCode;

        AuthThread(String url){
            this.url = url;
        }

        @Override
        public void run() {
            validateCode =  new RestTemplate().getForObject(url, ValidateCode.class);
        }

        public ValidateCode getValidateCode(){
            return validateCode;
        }

    }

    OauthProvider getProvider(String provider){
        return switch (provider) {
            case "Google" -> OauthProvider.Google;
            case "Facebook" -> OauthProvider.Facebook;
            case "Github" -> OauthProvider.Github;
            default -> null;
        };
    }

}
