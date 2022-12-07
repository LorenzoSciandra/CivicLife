package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
    Candidate findByCandidateId(String candidateId);
}
