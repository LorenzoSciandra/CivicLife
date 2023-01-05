package com.civiclife.oauthservice.controller;

import com.civiclife.oauthservice.ValidateCode;
import com.civiclife.oauthservice.model.Token;
import com.civiclife.oauthservice.repo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequestMapping("/authAPI/v1")
@RestController
public class AuthController {

    @Autowired
    TokenRepository tokenRepository;

    @GetMapping("/getToken")
    public HashMap<String, List<String>> getToken(OAuth2AuthenticationToken authentication) {
        String attributes = authentication.getPrincipal().getAttributes().toString().replace("{","").replace("}","");
        String name = attributes.split("given_name=")[1].split(",")[0];
        String surname = attributes.split("family_name=")[1].split(",")[0];
        String email = attributes.split("email=")[1];
        String token = attributes.split("at_hash=")[1].split(",")[0];

        HashMap<String, List<String>> risultato = new HashMap<>();
        Optional<Token> optionalTokenBean = tokenRepository.findById(email);
        HashMap<String, Instant> tokens_time;

        if(optionalTokenBean.isPresent()){
            Token tokenBean = optionalTokenBean.get();
            tokenBean.removeExpired();
            tokens_time = tokenBean.getTokens();
            List<String> active_tokens = new ArrayList<>(tokenBean.getTokens().keySet().stream().toList());
            active_tokens.add(token);
            risultato.put(email, active_tokens);
        }
        else{
            // registrare ipoteticamente nuovo utente
            tokens_time = new HashMap<>();
            risultato.put(email, new ArrayList<>(Collections.singleton(token)));
        }

        tokens_time.put(token, Instant.now());
        tokenRepository.save(new Token(email,tokens_time));

        return  risultato;
    }

    @GetMapping("/validate/{email}/{token}")
    public ValidateCode validate(@PathVariable(value = "email") String email, @PathVariable(value = "token") String token){
        Optional<Token> optionalTokenBean = tokenRepository.findById(email);
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

    @DeleteMapping("/deleteToken/{email}/{token}")
    public boolean deleteToken(@PathVariable(value = "email") String email, @PathVariable(value = "token") String token){
        Optional<Token> optionalTokenBean = tokenRepository.findById(email);
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

    @DeleteMapping("/deleteAllTokens/{email}")
    public boolean deleteAllTokens(@PathVariable(value = "email") String email){
        tokenRepository.deleteById(email);
        return true;
    }

}
