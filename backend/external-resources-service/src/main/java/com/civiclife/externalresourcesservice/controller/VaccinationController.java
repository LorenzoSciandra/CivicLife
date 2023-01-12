package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Vaccination;
import com.civiclife.externalresourcesservice.repository.VaccinationRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/vaccination/all")
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

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/vaccinations/{email_owner}/{email_richiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vaccination> getBVaccinationsByOwner(@PathVariable(value = "email_owner") String email_owner,
                                                     @PathVariable(value = "email_richiedente") String email_richiedente) {

       if(email_owner.equals(email_richiedente)){
           return vaccinationRepository.getVaccinationByEmail(email_owner);
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
