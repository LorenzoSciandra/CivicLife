package com.civiclife.oauthservice.controller;

import com.civiclife.oauthservice.config.ValidateCode;
import com.civiclife.oauthservice.model.Token;
import com.civiclife.oauthservice.model.TokenKey;
import com.civiclife.oauthservice.repo.TokenRepository;
//import com.civiclife.oauthservice.service.PublisherRabbit;
import com.civiclife.oauthservice.service.OAuth2UserService;
import com.nimbusds.oauth2.sdk.ResponseType;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequestMapping("/authAPI/v1")
@RestController
public class AuthController {

    @Autowired
    TokenRepository tokenRepository;

    @GetMapping("/token")
    public OAuth2AuthenticationToken token(OAuth2AuthenticationToken token){
        return token;
    }


    @GetMapping("/validationAuthorities")
    public List<TokenKey.OauthProvider> getValidationAuthorities() {
        return Arrays.asList(TokenKey.OauthProvider.values());
    }

    @GetMapping("/getAllTokens/{email}")
    public String getAllTokens(@PathVariable String email) {
        ArrayList<Optional<Token>> tokens = tokenRepository.getOauthCredentialsByEmail(email);
        StringBuilder result = new StringBuilder();
        if(!tokens.isEmpty()){
            for (Optional<Token> optionalToken : tokens) {
                if(optionalToken.isPresent()){
                    Token tokenBean = optionalToken.get();
                    String token = tokenBean.getMostRecentToken();
                    result.append(tokenBean.respose(token));
                }
            }
        }
        return result.toString();
    }

    @GetMapping("/getToken/{email}/{provider}")
    public String getToken(@PathVariable(value = "email") String email,
                           @PathVariable(value = "provider") TokenKey.OauthProvider provider) {
        String result = "";
        Optional<Token> optionalTokenBean = tokenRepository.findById(new TokenKey(email, provider));
        if (optionalTokenBean.isPresent()){
            Token tokenBean = optionalTokenBean.get();
            tokenBean.removeExpired();
            result = tokenBean.respose(tokenBean.getMostRecentToken());
        }
        return result;
    }

    @GetMapping("/validate/{email}/{token}/{provider}")
    public ValidateCode validate(@PathVariable(value = "email") String email,
                                 @PathVariable(value = "token") String token,
                                 @PathVariable(value = "provider") TokenKey.OauthProvider provider) {
        //System.out.println("SONO QUI PER VALIDARE");
        Optional<Token> optionalTokenBean = tokenRepository.findById(new TokenKey(email, provider));
        if (optionalTokenBean.isPresent()){
            Token tokenBean = optionalTokenBean.get();
            if(tokenBean.getTokens().containsKey(token)){
                Instant token_time = tokenBean.getTokens().get(token);
                if(ChronoUnit.HOURS.between(token_time, Instant.now()) >= 1){
                    tokenBean.removeExpired();
                    tokenRepository.save(tokenBean);
                    return ValidateCode.EXPIRED;
                }
                return ValidateCode.ACTIVE;
            }
            return ValidateCode.INVALID_TOKEN;
        }
        return ValidateCode.INVALID_EMAIL;
    }

    @DeleteMapping("/deleteToken/{token}")
    public boolean deleteToken(@RequestBody TokenKey tokenKey, @PathVariable(value = "token") String token){
        Optional<Token> optionalTokenBean = tokenRepository.findById(tokenKey);
        if(optionalTokenBean.isPresent()){
            Token tokenBean = optionalTokenBean.get();
            if(tokenBean.getTokens().containsKey(token)){
                tokenBean.getTokens().remove(token);
                tokenRepository.save(tokenBean);
            }
            return false;
        }
        return false;
    }

    /*

        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        String name = (String) attributes.get("name");
        String surname = (String) attributes.get("family_name");
        String email = (String) attributes.get("email");
        String token = (String) attributes.get("at_hash");
        boolean verified = (boolean) attributes.get("email_verified");
        HashMap<String, String> risultato = new HashMap<>();

        if(verified) {
            Optional<Token> optionalTokenBean = tokenRepository.findById(email);
            HashMap<String, Instant> tokens_time;

            if (optionalTokenBean.isPresent()) {
                Token tokenBean = optionalTokenBean.get();
                tokenBean.removeExpired();
                tokens_time = tokenBean.getTokens();

                if(!tokens_time.containsKey(token)){
                    risultato.put(email, token);
                    tokens_time.put(token, Instant.now());
                    tokenBean.setTokens(tokens_time);
                    tokenRepository.save(tokenBean);
                }
                else{
                    String mostRecentToken = tokenBean.getMostRecentToken();
                    risultato.put(email,mostRecentToken);
                }

            } else {
                String uri = "http://localhost:8080/userAPI/v1/user/createFromLogin/" + email + "/" + name + "/" + surname;
                RestTemplate restTemplate = new RestTemplate();
                boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

                if (result) {
                    tokens_time = new HashMap<>();
                    risultato.put(email, token);
                    tokens_time.put(token, Instant.now());
                    tokenRepository.save(new Token(email, tokens_time));
                }
            }
        }
        return risultato;



    @DeleteMapping("/deleteAllTokens/{email}")
    public boolean deleteAllTokens(@PathVariable(value = "email") String email){
        tokenRepository.deleteById(email);
        return true;
    }*/

}
