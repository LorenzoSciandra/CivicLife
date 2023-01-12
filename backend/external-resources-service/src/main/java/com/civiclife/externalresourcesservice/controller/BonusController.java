package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.repository.BonusRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bonusAPI/v1")
public class BonusController {

    private final BonusRepository bonusRepository;

    public BonusController(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @GetMapping("/bonus/all")
    public List<Bonus> getAllBonuses() {
        System.out.println("Get all Bonuses...");
        return bonusRepository.findAll();
    }


    @GetMapping("/bonus/{id}")
    public Bonus getBonusById(@PathVariable String id) {
        Optional<Bonus> bonus = bonusRepository.findById(id);
        return bonus.orElse(null);
    }

    @PostMapping("/bonus/create")
    public boolean createBonus(@RequestBody Bonus bonus) {
        bonusRepository.save(bonus);
        return true;
    }

    @DeleteMapping("/bonus/delete/{id}")
    public boolean deleteBonus(@PathVariable String id) {
        bonusRepository.deleteById(id);
        return true;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/bonuses/{email_owner}/{email_richiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bonus> getBonusesByOwner(@PathVariable(value = "email_owner") String email_owner,
                                         @PathVariable(value = "email_richiedente") String email_richiedente) {

        if(email_owner.equals(email_richiedente)){
            return bonusRepository.getBonusesByEmail(email_owner);
        }
        return new ArrayList<>();
    }

    @PostMapping("/bonus/update/{id}")
    public boolean updateBonus(@PathVariable String id, @RequestBody  Bonus bonus) {
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
