package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.ValidateCode;
import com.civiclife.externalresourcesservice.model.Vaccination;
import com.civiclife.externalresourcesservice.repository.VaccinationRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vaccinationAPI/v1")
public class VaccinationController {

    private final VaccinationRepository vaccinationRepository;

    public VaccinationController(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @GetMapping("/vaccinations")
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAll();
    }

    @GetMapping("/vaccination/{id}")
    public Vaccination getVaccinationsById(@PathVariable String id) {
        Optional<Vaccination> optionalVaccination = vaccinationRepository.findById(id);
        return optionalVaccination.orElse(null);
    }

    @PostMapping("/vaccination/create")
    public boolean createVaccination(@RequestBody Vaccination vaccination) {
        vaccinationRepository.save(vaccination);
        return true;
    }

    @DeleteMapping("/vaccination/delete/{id}")
    public boolean deleteVaccination(@PathVariable String id) {
        vaccinationRepository.deleteById(id);
        return true;
    }

    @GetMapping("/vaccinations/{email_owner}/{token}")
    public List<Vaccination> getBVaccinationsByOwner(@PathVariable String email_owner, @PathVariable String token) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_owner + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE){
            return vaccinationRepository.findAll().stream().filter(vaccination -> vaccination.getEmail_owner().equals(email_owner)).toList();
        }

        return new ArrayList<>();
    }

    @PostMapping("/vaccination/update/{id}")
    public boolean updateVaccination(@PathVariable String id, @RequestBody Vaccination vaccination) {
        Optional<Vaccination> optionalVaccination = vaccinationRepository.findById(id);
        if(optionalVaccination.isPresent()){
            Vaccination vaccinationToUpdate = optionalVaccination.get();
            vaccinationToUpdate.setDescription(vaccination.getDescription());
            vaccinationToUpdate.setDate(vaccination.getDate());
            vaccinationToUpdate.setVaccine(vaccination.getVaccine());
            vaccinationToUpdate.setDoctor(vaccination.getDoctor());
            vaccinationToUpdate.setLocation(vaccination.getLocation());
            vaccinationToUpdate.setType(vaccination.getType());
            vaccinationToUpdate.setName(vaccination.getName());
            vaccinationToUpdate.setManufacturer(vaccination.getManufacturer());
            vaccinationToUpdate.setNurse(vaccination.getNurse());
            vaccinationRepository.save(vaccinationToUpdate);
            return true;
        }
        return false;
    }

}
