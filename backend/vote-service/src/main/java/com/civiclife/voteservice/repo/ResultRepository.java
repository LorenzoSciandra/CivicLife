package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.model.Result;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResultRepository extends MongoRepository<Result, String> {

    @Query("{ 'votationId' : ?0, 'idParty' : ?0 }")
    Optional<Result> findResultByVotationAndPaAndPartyId(@Param("idVotation") String idVotation,
                                                         @Param("idParty") String idParty);
}
