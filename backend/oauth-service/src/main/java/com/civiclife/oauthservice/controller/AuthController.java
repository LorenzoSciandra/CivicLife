package com.civiclife.oauthservice.controller;

import com.civiclife.oauthservice.utils.AES;
import com.civiclife.oauthservice.utils.ValidateCode;
import com.civiclife.oauthservice.model.Token;
import com.civiclife.oauthservice.model.TokenKey;
import com.civiclife.oauthservice.repo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequestMapping("/authAPI/v1")
@RestController

public class AuthController {

    @Autowired
    TokenRepository tokenRepository;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/token/{encryptToken}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String token(@PathVariable(value = "encryptToken") String encryptToken){
        // Base64 to utf8
        String encrypTokenUTF8 = new String(Base64.getDecoder().decode(encryptToken));
        String secret = "!CivicLifeSecret2023!";
        String token = AES.decrypt(encrypTokenUTF8, secret);
        if(token == null){
            return "";
        }
        return Base64.getEncoder().encodeToString(token.getBytes());
    }


    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping("/getAllTokens/{email}")
    public String getAllTokens(@PathVariable(value = "email") String email) {
        List<Optional<Token>> tokens = tokenRepository.getOauthCredentialsByEmail(email);
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

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
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

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/validate/{email}/{token}/{provider}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidateCode validate(@PathVariable(value = "email") String email,
                                 @PathVariable(value = "token") String token,
                                 @PathVariable(value = "provider") TokenKey.OauthProvider provider) {
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

        List<Optional<Token>> tokensByEmail = tokenRepository.getOauthCredentialsByEmail(email);
        if (tokensByEmail.isEmpty()){
            return ValidateCode.INVALID_EMAIL;
        }
        return ValidateCode.INVALID_PROVIDER;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @PostMapping(value = "/deleteToken/{email}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteToken(@RequestBody String token,
                               @PathVariable(value = "email") String email) {

        TokenKey tokenkey = parseToken(token.replace("{", "").replace("}", "").replace("\"", ""));;
        if(tokenkey != null){
            Optional<Token> optionalTokenBean = tokenRepository.findById(tokenkey);
            if (optionalTokenBean.isPresent()){
                Token tokenBean = optionalTokenBean.get();
                if(tokenBean.getTokenKey().getEmail().equals(email)){
                    tokenBean.removeToken(token);
                    tokenRepository.save(tokenBean);
                    return true;
                }
            }
        }
        return false;
    }

    private TokenKey parseToken(String token){
        System.out.println("Parser: " + token);
        TokenKey tokenKey = new TokenKey();
        String[] campi = token.split(",");

        if(campi.length == 2){
            for (String campo : campi) {
                String[] keyValue = campo.split(":");
                String key = campo.split(":")[0];
                String value = "";
                if(keyValue.length > 1){
                    value = campo.split(":")[1];

                    switch (key) {
                        case "email" -> {
                            if(value.contains("@")) {
                                tokenKey.setEmail(value);
                            }
                            else{
                                return null;
                            }
                        }
                        case "provider" -> {
                            TokenKey.OauthProvider provider = getParseProvider(value);
                            if (provider != null) {
                                tokenKey.setProvider(provider);
                            } else {
                                return null;
                            }
                        }
                    }
                }
                else{
                    return null;
                }

            }
            return tokenKey;

        }

        return null;

    }

    private TokenKey.OauthProvider getParseProvider(String provider){
        return switch (provider) {
            case "Google" -> TokenKey.OauthProvider.Google;
            case "Facebook" -> TokenKey.OauthProvider.Facebook;
            case "Github" -> TokenKey.OauthProvider.Github;
            default -> null;
        };
    }

}
