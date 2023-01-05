package com.civiclife.oauthservice.repo;

import com.civiclife.oauthservice.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {

}
