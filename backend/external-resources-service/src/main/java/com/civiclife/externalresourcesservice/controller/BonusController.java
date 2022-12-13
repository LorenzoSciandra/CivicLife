package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.repo.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BonusController {

    @Autowired
    BonusRepository bonusRepository;

    @GetMapping("/bonuses")
    public List<Bonus> getAllBonuses() {
        return bonusRepository.findAllBonus();
    }

    @GetMapping("/bonus/{id}")
    public Bonus getBonusById(Long id) {
        Optional<Bonus> bonus = bonusRepository.findById(id);
        return bonus.orElse(null);
    }

    @PostMapping("/bonuses/create")
    public boolean createBonus(Bonus bonus) {
        bonusRepository.save(bonus);
        return true;
    }

    @GetMapping("/bonuses/delete/{id}")
    public boolean deleteBonus(Long id) {
        bonusRepository.deleteById(id);
        return true;
    }

    @GetMapping("/bonuses/{id_owner}")
    public List<Bonus> getBonusesByOwner(String idOwner) {
        return bonusRepository.findAllBonusByIdOwner(idOwner);
    }

    @PostMapping("/bonuses/update/{id}")
    public boolean updateBonus(Long id, Bonus bonus) {
        Optional<Bonus> optionalBonus = bonusRepository.findById(id);
        if(optionalBonus.isPresent()){
            Bonus bonusToUpdate = optionalBonus.get();
            bonusToUpdate.setName(bonus.getName());
            bonusToUpdate.setDescription(bonus.getDescription());
            bonusToUpdate.setType(bonus.getType());
            bonusToUpdate.setStartDate(bonus.getStartDate());
            bonusToUpdate.setEndDate(bonus.getEndDate());
            bonusRepository.save(bonusToUpdate);
            return true;
        }
        return false;
    }

}
