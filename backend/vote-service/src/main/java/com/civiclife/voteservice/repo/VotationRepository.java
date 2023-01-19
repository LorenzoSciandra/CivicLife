package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.model.Votation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VotationRepository extends MongoRepository<Votation, String> {

    @Query("{ 'status' : ?0 }")
    List<Votation> votationsByStatus(Votation.VotationStatus status);

}
