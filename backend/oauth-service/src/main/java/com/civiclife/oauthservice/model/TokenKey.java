package com.civiclife.oauthservice.model;

public class TokenKey {

    public enum OauthProvider {
        Google,
        Facebook,
        Github
    }

    private String email;
    private OauthProvider provider;

    public TokenKey(String email, OauthProvider provider) {
        this.email = email;
        this.provider = provider;
    }

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

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' + "," +
                "provider=" + provider +
                '}';
    }
}
