package com.civiclife.initiativeservice.repo;

import com.civiclife.initiativeservice.model.Initiative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InitiativeRepository extends MongoRepository<Initiative, String> {

}
