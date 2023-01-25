package com.civiclife.oauthservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Data
@Document(collection = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Token {

    @Id
    private TokenKey tokenKey;
    private HashMap<String, Instant> tokens;

    public void removeExpired(){
        Instant now = Instant.now();
        ArrayList<String> keys = tokens.keySet().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        for(int i = 0; i < tokens.size(); i++){
            if(ChronoUnit.HOURS.between(tokens.get(keys.get(i)), now) >= 1){
                tokens.remove(keys.get(i));
            }
        }
    }

    public String getMostRecentToken(){
        long min_time = Long.MAX_VALUE;
        String token = "";
        Instant now = Instant.now();
        ArrayList<String> keys = tokens.keySet().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        for(int i = 0; i < tokens.size(); i++){
            if(ChronoUnit.SECONDS.between(tokens.get(keys.get(i)), now) < min_time){
                min_time = ChronoUnit.SECONDS.between(tokens.get(keys.get(i)), now);
                token = keys.get(i);
            }
        }

        return token;
    }

    public void removeToken(String token){
        tokens.remove(token);
    }

    public TokenKey getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(TokenKey tokenKey) {
        this.tokenKey = tokenKey;
    }

    public HashMap<String, Instant> getTokens() {
        return tokens;
    }

    public void setTokens(HashMap<String, Instant> tokens) {
        this.tokens = tokens;
    }

    public String respose(String token) {
        return "{" +
                "tokenKey:" + tokenKey + "," +
                "token: " + token +
                '}';
    }
}