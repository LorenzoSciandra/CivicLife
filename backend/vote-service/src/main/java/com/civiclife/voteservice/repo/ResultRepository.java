package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.model.Result;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ResultRepository extends MongoRepository<Result, String> {
    @Query("{ 'id' : ?0 }")
    Optional<Result> findResultById(String id);
}
