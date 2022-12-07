package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Party;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartyRepository extends MongoRepository<Party, String> {
    Party findByPartyId(String partyId);
}
