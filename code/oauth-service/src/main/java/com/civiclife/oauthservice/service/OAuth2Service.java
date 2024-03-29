package com.civiclife.oauthservice.service;

import com.civiclife.oauthservice.component.UserComponent;
import com.civiclife.oauthservice.model.Token;
import com.civiclife.oauthservice.model.TokenKey;
import com.civiclife.oauthservice.repo.TokenRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OAuth2Service {

    private TokenRepository tokenRepository;

    private ProducerService producerService;

    @Autowired
    public OAuth2Service(ProducerService producerService, TokenRepository tokenRepository) {
        this.producerService = producerService;
        this.tokenRepository = tokenRepository;
    }

    public String processOauthPostLogin(OAuth2AuthenticationToken authData) throws ResponseStatusException {

        String oauth2ProviderName = StringUtils.capitalize(authData.getAuthorizedClientRegistrationId());
        TokenKey.OauthProvider oauth2Provider = TokenKey.OauthProvider.valueOf(oauth2ProviderName);
        OAuth2User principal = authData.getPrincipal();
        String oauth2UserEmail;
        String oauth2UserName;
        String oauth2UserSurname;
        String token;
        String details;

        //System.out.println("oauth2ProviderName: " + oauth2ProviderName);
        //System.out.println(principal.getAttributes().toString());

        switch (TokenKey.OauthProvider.valueOf(oauth2ProviderName)) {
            case Google -> {
                oauth2UserEmail = principal.getAttribute("email");
                oauth2UserName = principal.getAttribute("given_name");
                oauth2UserSurname = principal.getAttribute("family_name");
                token = principal.getAttribute("at_hash");
            }
            case Github -> {
                oauth2UserEmail = principal.getAttribute("email");
                oauth2UserName = Objects.requireNonNull(principal.getAttribute("name"));
                oauth2UserSurname = "";
                details = authData.getDetails().toString();
                token = details.substring(details.indexOf("SessionId="))
                        .replaceAll("]", "")
                        .replaceAll(",", "")
                        .replaceAll("SessionId=", "");
            }
            case Facebook -> {
                oauth2UserName = Objects.requireNonNull(principal.getAttribute("name"));
                oauth2UserSurname = "";
                oauth2UserEmail = principal.getAttribute("email");
                token = "";
                details = authData.getDetails().toString();
                token = details.substring(details.indexOf("SessionId="))
                        .replaceAll("]", "")
                        .replaceAll(",", "")
                        .replaceAll("SessionId=", "");
            }
            default -> {
                oauth2UserEmail = "";
                oauth2UserName = "";
                oauth2UserSurname = "";
                token = "";
            }
        }


        Optional<Token> loadedCredentials = tokenRepository.findById(new TokenKey(oauth2UserEmail, oauth2Provider));

        if (loadedCredentials.isPresent()) {
            Token tokenBean = loadedCredentials.get();
            HashMap<String, Instant> tokens_time = tokenBean.getTokens();

            if(!tokens_time.containsKey(token)){
                tokens_time.put(token, Instant.now());
                tokenBean.setTokens(tokens_time);
            }
            tokenRepository.save(tokenBean);

        } else {

            UserComponent userComponent = new UserComponent();
            userComponent.setMail(oauth2UserEmail);
            userComponent.setName(oauth2UserName);
            userComponent.setSurname(oauth2UserSurname);
            producerService.sendMessage(userComponent);

            HashMap<String, Instant> tokens_time = new HashMap<>();
            tokens_time.put(token, Instant.now());
            tokenRepository.save(new Token(new TokenKey(oauth2UserEmail, oauth2Provider), tokens_time));
        }
        return "email: " + oauth2UserEmail + ",provider: " + oauth2ProviderName + ",token: " + token;
    }
}