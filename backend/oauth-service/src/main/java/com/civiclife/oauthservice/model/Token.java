package com.civiclife.oauthservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Data
@Document(collection = "tokens")
public class Token {

    @Id
    private String email;
    private HashMap<String, Instant> tokens;

    public Token(){

    }
    public Token(String email, HashMap<String, Instant> tokens) {
        this.email = email;
        this.tokens = tokens;
    }

    public void removeExpired(){
        ArrayList<String> keys = tokens.keySet().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        for(int i = 0; i < tokens.size(); i++){
            if(ChronoUnit.HOURS.between(tokens.get(keys.get(i)), Instant.now()) >= 1){
                tokens.remove(keys.get(i));
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, Instant> getTokens() {
        return tokens;
    }

    public void setTokens(HashMap<String, Instant> tokens) {
        this.tokens = tokens;
    }
}
