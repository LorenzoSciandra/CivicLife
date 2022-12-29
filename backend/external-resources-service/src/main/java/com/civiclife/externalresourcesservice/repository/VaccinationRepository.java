package com.civiclife.externalresourcesservice.repository;

import com.civiclife.externalresourcesservice.model.Vaccination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {

}
