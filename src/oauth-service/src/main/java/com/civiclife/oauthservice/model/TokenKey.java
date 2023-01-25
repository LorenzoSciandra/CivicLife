package com.civiclife.oauthservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenKey {

    public enum OauthProvider {
        Google,
        Facebook,
        Github
    }

    private String email;
    private OauthProvider provider;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OauthProvider getProvider() {
        return provider;
    }

    public void setProvider(OauthProvider provider) {
        this.provider = provider;
    }


}
