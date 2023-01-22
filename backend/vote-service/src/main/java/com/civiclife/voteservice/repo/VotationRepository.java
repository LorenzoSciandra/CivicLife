package com.civiclife.voteservice.repo;

import com.civiclife.voteservice.model.Votation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Set;

public interface VotationRepository extends MongoRepository<Votation, String> {

    @Query("{ 'status' : ?0 }")
    Set<Votation> votationsByStatus(@Param("status") Votation.VotationStatus status);

    @Query("{'votationResult.votersIdList': {$elemMatch: {$eq: ?0}}}")
    Set<Votation> votationsDoneByUser(@Param("idVoter") String idVoter);

}
