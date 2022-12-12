package com.civiclife.initiativeservice.repo;

import com.civiclife.initiativeservice.model.Initiative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InitiativeRepository extends MongoRepository<Initiative, String> {
    @Query("{ 'id' : ?0 }")
    Initiative findById(int id);

    @Query("{ 'name' : ?0 }")
    Initiative findByName(String name);

}
