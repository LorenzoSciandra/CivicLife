package com.civiclife.externalresourcesservice.repo;

import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.model.Vaccination;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VaccinationRepository extends CrudRepository<Vaccination, Long> {

    List<Vaccination> findAllVaccinations();

    List<Vaccination> findAllVaccinationByIdUser(String idOwner);
}
