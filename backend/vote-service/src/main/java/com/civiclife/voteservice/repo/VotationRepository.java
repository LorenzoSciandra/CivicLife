package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Votation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotationRepository extends MongoRepository<Votation, String> {
    Votation findByVotationId(String votationId);

}
