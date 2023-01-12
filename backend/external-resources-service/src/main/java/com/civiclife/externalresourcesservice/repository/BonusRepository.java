package com.civiclife.externalresourcesservice.repository;

import com.civiclife.externalresourcesservice.model.Bonus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonusRepository extends MongoRepository<Bonus, String> {
    @Query("{ 'email_owner' : ?0 }")
    List<Bonus> getBonusesByEmail(
            @Param("email_owner") String email_owner
    );
}
