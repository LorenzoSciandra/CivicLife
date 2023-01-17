package com.civiclife.initiativeservice.repo;

import com.civiclife.initiativeservice.model.Initiative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InitiativeRepository extends MongoRepository<Initiative, String> {

    @Query("{ 'idCreator' : ?0 }")
    List<Initiative> findInitiativesByCreator( @Param("idCreator") String idCreator);

    @Query("{'idOrganizers': {$elemMatch: {$eq: ?0}}}")
    List<Initiative> findInitiativesByOrganizer( @Param("idOrganizer") String idOrganizer);

    @Query("{'idMembers': {$elemMatch: {$eq: ?0}}}")
    List<Initiative> findInitiativeByMember( @Param("idMember") String idMember);

}
