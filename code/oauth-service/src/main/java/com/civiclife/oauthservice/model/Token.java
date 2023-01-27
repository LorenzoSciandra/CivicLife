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
public class Token {

    @Id
    private TokenKey tokenKey;
    private HashMap<String, Instant> tokens;

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

    public String toString(String token) {
        return "{" +
                "tokenKey:" + tokenKey + "," +
                "token: " + token +
                '}';
    }
}
