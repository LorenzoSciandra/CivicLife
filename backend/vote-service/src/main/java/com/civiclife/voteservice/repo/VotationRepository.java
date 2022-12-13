package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.model.Votation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface VotationRepository extends MongoRepository<Votation, String> {
    @Query("{ 'id' : ?0 }")
    Optional<Votation> findVotationById(String id);

}
