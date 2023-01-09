package com.civiclife.externalresourcesservice.repository;

import com.civiclife.externalresourcesservice.model.Bonus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusRepository extends MongoRepository<Bonus, String> {

}
