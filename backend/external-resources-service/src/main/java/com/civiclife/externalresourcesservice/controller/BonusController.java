package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.repository.BonusRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bonusAPI/v1")
public class BonusController {

    private final BonusRepository bonusRepository;

    public BonusController(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @GetMapping("/bonuses")
    public List<Bonus> getAllBonuses() {
        System.out.println("Get all Bonuses...");
        return bonusRepository.findAll();
    }

    @GetMapping("/bonus/{id}")
    public Bonus getBonusById(@PathVariable Long id) {
        Optional<Bonus> bonus = bonusRepository.findById(id);
        return bonus.orElse(null);
    }

    @PostMapping("/bonus/create")
    public boolean createBonus(@RequestBody Bonus bonus) {
        bonusRepository.save(bonus);
        return true;
    }

    @DeleteMapping("/bonus/delete/{id}")
    public boolean deleteBonus(@PathVariable Long id) {
        bonusRepository.deleteById(id);
        return true;
    }

    @GetMapping("/bonuses/{id_owner}")
    public List<Bonus> getBonusesByOwner(@PathVariable String id_owner) {
        return bonusRepository.findAll().stream().filter(bonus -> bonus.getId_owner().equals(id_owner)).toList();
    }

    @PostMapping("/bonus/update/{id}")
    public boolean updateBonus(@PathVariable Long id, @RequestBody  Bonus bonus) {
        Optional<Bonus> optionalBonus = bonusRepository.findById(id);
        if(optionalBonus.isPresent()){
            Bonus bonusToUpdate = optionalBonus.get();
            bonusToUpdate.setName(bonus.getName());
            bonusToUpdate.setDescription(bonus.getDescription());
            bonusToUpdate.setType(bonus.getType());
            bonusToUpdate.setEnd_date(bonus.getEnd_date());
            bonusRepository.save(bonusToUpdate);
            return true;
        }
        return false;
    }

}
