package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
    @Query("{ 'id' : ?0 }")
    Optional<Candidate> findCandidateById(String id);

}
