package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Result;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultRepository extends MongoRepository<Result, String> {
    Result findByResultId(String resultId);
}
