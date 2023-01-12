package com.civiclife.externalresourcesservice.repository;

import com.civiclife.externalresourcesservice.model.Vaccination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {


    @Query("{ 'email_owner' : ?0 }")
    List<Vaccination> getVaccinationByEmail(
            @Param("email_owner") String email_owner
    );

}
