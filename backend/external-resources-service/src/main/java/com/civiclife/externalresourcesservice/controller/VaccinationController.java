package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Vaccination;
import com.civiclife.externalresourcesservice.repository.VaccinationRepository;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/vaccinations/{id_owner}")
    public List<Vaccination> getBVaccinationsByOwner(@PathVariable String id_owner) {
        return vaccinationRepository.findAll().stream().filter(vaccination -> vaccination.getId_owner().equals(id_owner)).toList();
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
