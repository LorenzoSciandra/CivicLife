package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.model.Vaccination;
import com.civiclife.externalresourcesservice.repository.VaccinationRepository;
import com.civiclife.externalresourcesservice.utils.ErrorMessage;
import com.civiclife.externalresourcesservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vaccinationAPI/v1")
public class VaccinationController {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 1000)
    @GetMapping(value = "/vaccinations/{email_owner}/{email_richiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vaccination> getBVaccinationsByOwner(@PathVariable(value = "email_owner") String email_owner,
                                                     @PathVariable(value = "email_richiedente") String email_richiedente) {

       if(email_owner.equals(email_richiedente)){
           return vaccinationRepository.getVaccinationByEmail(email_owner);
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
    public boolean createVaccinationPostman(@RequestBody Vaccination[] vaccinations) {
        vaccinationRepository.saveAll(List.of(vaccinations));
        return true;
    }

    @GetMapping("postman/vaccination/all")
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAll();
    }

    @DeleteMapping("postman/vaccination/delete/{id}")
    public boolean deleteVaccination(@PathVariable String id) {
        vaccinationRepository.deleteById(id);
        return true;
    }

    @GetMapping("postman/vaccination/{id}")
    public Vaccination getVaccinationsById(@PathVariable String id) {
        Optional<Vaccination> optionalVaccination = vaccinationRepository.findById(id);
        return optionalVaccination.orElse(null);
    }

}
