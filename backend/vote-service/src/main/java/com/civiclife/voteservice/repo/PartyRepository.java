package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Party;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PartyRepository extends MongoRepository<Party, String> {

    @Query("{ 'id' : ?0 }")
    Optional<Party> findPartyById(String id);

}
