package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.repository.BonusRepository;
import com.civiclife.externalresourcesservice.utils.ErrorMessage;
import com.civiclife.externalresourcesservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bonusAPI/v1")
public class BonusController {

    @Autowired
    private BonusRepository bonusRepository;


    @CrossOrigin(origins = "http://localhost:3000", maxAge = 1000)
    @GetMapping(value = "/bonuses/{email_owner}/{email_richiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bonus> getBonusesByOwner(@PathVariable(value = "email_owner") String email_owner,
                                         @PathVariable(value = "email_richiedente") String email_richiedente) {

        if(email_owner.equals(email_richiedente)){
            return bonusRepository.getBonusesByEmail(email_owner);
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 1000)
    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    // FOR POSTMAN TEST: CREATE AND DELETE  

    @PostMapping(value="/postman/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean createBonusPostman(@RequestBody Bonus[] bonuses) {
        bonusRepository.saveAll(List.of(bonuses));
        return true;
    }

    @PostMapping("postman/bonus/create")
    public boolean createBonus(@RequestBody Bonus[] bonuses) {
        bonusRepository.saveAll(List.of(bonuses));
        return true;
    }

    @DeleteMapping("postman/bonus/delete/{id}")
    public boolean deleteBonus(@PathVariable String id) {
        bonusRepository.deleteById(id);
        return true;
    }

    @GetMapping("postman/bonus/all")
    public List<Bonus> getAllBonuses() {
        return bonusRepository.findAll();
    }

    @GetMapping(value = "postman/bonus/{id}")
    public Bonus getBonusById(@PathVariable String id) {
        Optional<Bonus> bonus = bonusRepository.findById(id);
        return bonus.orElse(null);
    }

}
