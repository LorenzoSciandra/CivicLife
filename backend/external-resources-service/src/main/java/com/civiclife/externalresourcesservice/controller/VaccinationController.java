package com.civiclife.externalresourcesservice.controller;

import com.civiclife.externalresourcesservice.model.Bonus;
import com.civiclife.externalresourcesservice.model.Vaccination;
import com.civiclife.externalresourcesservice.repo.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class VaccinationController {

    @Autowired
    VaccinationRepository vaccinationRepository;


    @GetMapping("/vaccinations")
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAllVaccinations();
    }

    @GetMapping("/vaccinations/{id}")
    public Vaccination getVaccinationsById(long id) {
        Optional<Vaccination> optionalVaccination = vaccinationRepository.findById(id);
        return optionalVaccination.orElse(null);
    }

    @PostMapping("/vaccination/create")
    public boolean createVaccination(Vaccination vaccination) {
        vaccinationRepository.save(vaccination);
        return true;
    }

    @GetMapping("/vaccinations/delete/{id}")
    public boolean deleteBonus(Long id) {
        vaccinationRepository.deleteById(id);
        return true;
    }

    @GetMapping("/vaccinations/{id_owner}")
    public List<Vaccination> getBonusesByOwner(String idOwner) {
        return vaccinationRepository.findAllVaccinationByIdUser(idOwner);
    }

    @PostMapping("/vaccinations/update/{id}")
    public boolean updateVaccination(Long id, Vaccination vaccination) {
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
