package com.civiclife.oauthservice.repo;

import com.civiclife.oauthservice.model.Token;
import com.civiclife.oauthservice.model.TokenKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, TokenKey> {

    @Query("{ 'tokenKey.email' : ?0 }")
    ArrayList<Optional<Token>> getOauthCredentialsByEmail(
            @Param("email") String email
    );

}
