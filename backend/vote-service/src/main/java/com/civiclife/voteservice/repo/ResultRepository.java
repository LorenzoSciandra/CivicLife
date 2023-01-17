package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.model.ResultId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResultRepository extends MongoRepository<Result, ResultId> {

    @Query("{ 'id.votationId' : ?0, 'id.partyId' : ?0 }")
    Optional<Result> findResultByVotationAndPaAndPartyId(String votationId, String partyId);
}
