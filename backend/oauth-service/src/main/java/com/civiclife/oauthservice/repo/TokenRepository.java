package com.civiclife.oauthservice.repo;

import com.civiclife.oauthservice.model.Token;
import com.civiclife.oauthservice.model.TokenKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, TokenKey> {

    @Query("{ 'email' : ?0, 'provider' : ?1 }")
    Optional<Token> getOauthCredentialsByUserEmailAndProvider(
            @Param("email") String email,
            @Param("provider") TokenKey.OauthProvider provider
    );

}
