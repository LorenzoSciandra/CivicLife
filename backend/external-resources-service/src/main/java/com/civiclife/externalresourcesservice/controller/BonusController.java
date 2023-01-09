package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.ValidateCode;
import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.repository.BonusRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/bonuses")
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

    @GetMapping("/bonuses/{email_owner}/{token}")
    public List<Bonus> getBonusesByOwner(@PathVariable String email_owner, @PathVariable String token) {
        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_owner + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE){
            return bonusRepository.findAll().stream().filter(bonus -> bonus.getEmail_owner().equals(email_owner)).toList();

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
