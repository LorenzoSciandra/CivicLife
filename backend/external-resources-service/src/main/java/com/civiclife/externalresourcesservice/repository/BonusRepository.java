package com.civiclife.externalresourcesservice.repository;

import com.civiclife.externalresourcesservice.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
}
