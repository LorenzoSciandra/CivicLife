package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Party;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartyRepository extends MongoRepository<Party, String> {

}
