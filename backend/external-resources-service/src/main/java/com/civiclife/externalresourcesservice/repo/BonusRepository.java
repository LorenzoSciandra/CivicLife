package com.civiclife.externalresourcesservice.repo;

import com.civiclife.externalresourcesservice.model.Bonus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BonusRepository extends CrudRepository<Bonus, Long> {

    List<Bonus> findAllBonus();

    List<Bonus> findAllBonusByIdOwner(String idOwner);
}