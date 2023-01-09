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
public class OAuth2UserService {

    private TokenRepository tokenRepository;

    private ProducerService producerService;

    @Autowired
    public OAuth2UserService(ProducerService producerService, TokenRepository tokenRepository) {
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

        System.out.println("oauth2ProviderName: " + oauth2ProviderName);
        System.out.println(principal.getAttributes().toString());

        switch (TokenKey.OauthProvider.valueOf(oauth2ProviderName)) {
            case Google:
                oauth2UserEmail = principal.getAttribute("email");
                oauth2UserName = principal.getAttribute("given_name");
                oauth2UserSurname = principal.getAttribute("family_name");
                token = principal.getAttribute("at_hash");
                break;
            case Github:
                oauth2UserEmail = principal.getAttribute("email");
                oauth2UserName = ((String) Objects.requireNonNull(principal.getAttribute("name"))).split(" ")[0];
                oauth2UserSurname = ((String) Objects.requireNonNull(principal.getAttribute("name"))).split(" ")[1];
                token = principal.getAttribute("node_id");
                break;
            default:
                oauth2UserEmail = "";
                oauth2UserName = "";
                oauth2UserSurname = "";
                token = "";
                break;
        }


        Optional<Token> loadedCredentials = tokenRepository.findById(new TokenKey(oauth2UserEmail, oauth2Provider));

        if (loadedCredentials.isPresent()) {
            Token tokenBean = loadedCredentials.get();
            tokenBean.removeExpired();
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

            /*HashMap<String, Instant> tokens_time = new HashMap<>();
            tokens_time.put(token, Instant.now());
            tokenRepository.save(new Token(new TokenKey(oauth2UserEmail, oauth2Provider), tokens_time));*/
        }
        return "http://localhost:8080/authAPI/v1/getToken/" + oauth2UserEmail + "/" + oauth2Provider;
    }
}


/*
            String uri = "http://localhost:8080/userAPI/v1/user/createFromLogin/" + oauth2UserEmail + "/" + oauth2UserName + "/" + oauth2UserSurname;
            RestTemplate restTemplate = new RestTemplate();
            boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

            if (result) {
                HashMap<String, Instant> tokens_time = new HashMap<>();
                tokens_time.put(token, Instant.now());
                tokenRepository.save(new Token(new TokenKey(oauth2UserEmail, oauth2Provider), tokens_time));
                return "http://localhost:8080/authAPI/v1/getToken/" + oauth2UserEmail + "/" + oauth2Provider;
            }
            else{
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format("%s non è un provider Oauth2 autorizzato per questo account.",
                                oauth2ProviderName)
                );
            }
            HashMap<String, Instant> tokens_time = new HashMap<>();
            tokens_time.put(token, Instant.now());
            tokenRepository.save(new Token(new TokenKey(oauth2UserEmail, oauth2Provider), tokens_time));*/