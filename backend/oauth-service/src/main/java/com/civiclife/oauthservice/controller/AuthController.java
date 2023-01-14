package com.civiclife.oauthservice.controller;

import com.civiclife.oauthservice.utility.AES;
import com.civiclife.oauthservice.utility.ValidateCode;
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
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
public class AuthController {

    @Autowired
    TokenRepository tokenRepository;
    private final String secret = "!CivicLifeSecret2023!";

    @GetMapping(value = "/token/{encrypToken}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String token(@PathVariable(value = "encrypToken") String encrypToken){
        // Base64 to utf8
        String encrypTokenUTF8 = new String(Base64.getDecoder().decode(encrypToken));
        String token = AES.decrypt(encrypTokenUTF8, secret);
        return Base64.getEncoder().encodeToString(token.getBytes());
    }


    @GetMapping("/validationAuthorities")
    public List<TokenKey.OauthProvider> getValidationAuthorities() {
        return Arrays.asList(TokenKey.OauthProvider.values());
    }

    @GetMapping("/getAllTokens/{email}")
    public String getAllTokens(@PathVariable(value = "email") String email) {
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

    @GetMapping(value = "/validate/{email}/{token}/{provider}",
            produces = MediaType.APPLICATION_JSON_VALUE)
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

        ArrayList<Optional<Token>> tokensByEmail = tokenRepository.getOauthCredentialsByEmail(email);
        if (tokensByEmail.isEmpty()){
            return ValidateCode.INVALID_EMAIL;
        }
        return ValidateCode.INVALID_PROVIDER;
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
}
