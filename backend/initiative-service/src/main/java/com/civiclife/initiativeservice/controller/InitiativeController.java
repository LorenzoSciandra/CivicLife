package com.civiclife.initiativeservice.controller;

import com.civiclife.initiativeservice.ValidateCode;
import com.civiclife.initiativeservice.model.Initiative;
import com.civiclife.initiativeservice.repo.InitiativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/initiativeAPI/v1")
public class InitiativeController {

    @Autowired
    private InitiativeRepository initiativeRepository;

    @GetMapping("/initiatives")
    public List<Initiative> getInitiatives() {
        return initiativeRepository.findAll();
    }

    @GetMapping("/initiative/{id}")
    public Initiative getInitiativeById(@PathVariable (value = "id") String id){
        return initiativeRepository.findById(id).orElse(null);
    }

    @GetMapping("/initiative/remove/{id}/{email_creator}/{token}")
    public boolean removeInitiative(@PathVariable(value = "id") String id,
                                    @PathVariable(value = "email_creator") String email_creator,
                                    @PathVariable(value = "token") String token) {

        Optional<Initiative> optionalInitiative = initiativeRepository.findById(id);

        if(optionalInitiative.isPresent()){
            String uri = "http://localhost:8080/authAPI/v1/validate/" + email_creator + "/" + token;
            RestTemplate restTemplate = new RestTemplate();
            ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);
            Initiative initiative = optionalInitiative.get();

            if(result == ValidateCode.ACTIVE && initiative.getIdCreator().equals(email_creator)){
                initiativeRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    @PostMapping("/initiative/create/{email_creator}/{token}")
    public boolean createInitiative(@PathVariable(value = "email_creator") String email_creator,
                                    @PathVariable(value = "token") String token,
                                    @RequestBody Initiative initiative) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_creator + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if (result == ValidateCode.ACTIVE){
            initiative.setIdCreator(email_creator);
            initiative.setIdOrganizers(new ArrayList<>(Collections.singleton(email_creator)));
            initiative.setIdMembers(new ArrayList<>(Collections.singleton(email_creator)));
            initiativeRepository.save(initiative);
            return true;
        }
        return false;
    }

    @PostMapping("/initiative/modify/{id}/{email_org}/{token}")
    public boolean modifyInitiative(@RequestBody Initiative updateInitiative,
                                    @PathVariable(value = "id") String id,
                                    @PathVariable(value = "email_org") String email_org,
                                    @PathVariable(value = "token") String token){


        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_org + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if (result == ValidateCode.ACTIVE){
            Optional<Initiative> optionalOriginalInitiative = initiativeRepository.findById(id);

            if(optionalOriginalInitiative.isPresent()){
                Initiative originalInitiative = optionalOriginalInitiative.get();

                if(originalInitiative.getIdOrganizers().contains(email_org)) {
                    modifyInitiative(updateInitiative, originalInitiative);
                    initiativeRepository.save(originalInitiative);
                    return true;
                }

            }
        }

        return false;
    }

    @GetMapping("/initiative/{idInitiative}/subscribe/{email_user}/{token}")
    public boolean subscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                       @PathVariable(value = "email_user") String email_user,
                                       @PathVariable(value = "token") String token){

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_user + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE){
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if(initiative.isPresent()){
                Initiative initiativeToModify = initiative.get();
                if(!initiativeToModify.getIdMembers().contains(email_user)){
                    initiativeToModify.getIdMembers().add(email_user);
                    initiativeRepository.save(initiativeToModify);
                    return true;
                }
            }
        }

        return false;

    }

    @GetMapping("/initiative/{idInitiative}/unsubscribe/{email_user}/{token}")
    public boolean unsubscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                         @PathVariable(value = "email_user") String email_user,
                                         @PathVariable(value = "token") String token){

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_user + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if (initiative.isPresent()) {
                Initiative initiativeToModify = initiative.get();

                if (initiativeToModify.getIdMembers().contains(email_user)) {
                    initiativeToModify.getIdMembers().remove(email_user);
                    initiativeRepository.save(initiativeToModify);
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/initiative/{idInitiative}/{new_org}/addOrganizer/{email_org}/{token}")
    public boolean addOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                          @PathVariable(value = "new_org") String new_org,
                                          @PathVariable(value = "email_org") String email_org,
                                          @PathVariable(value = "token") String token){

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_org + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if (result == ValidateCode.ACTIVE) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if (initiative.isPresent()) {
                Initiative initiativeToModify = initiative.get();

                if (!initiativeToModify.getIdOrganizers().contains(email_org)) {
                    initiativeToModify.getIdOrganizers().add(new_org);

                    if(!initiativeToModify.getIdMembers().contains(new_org)){
                        initiativeToModify.getIdMembers().add(new_org);
                    }

                    initiativeRepository.save(initiativeToModify);
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/initiative/{idInitiative}/removeOrganizer/{email_org}/{email_user}/{token}")
    public boolean removeOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                             @PathVariable(value = "email_org") String email_org,
                                             @PathVariable(value = "email_user") String email_user,
                                             @PathVariable(value = "token") String token){

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_user + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE) {

            Optional<Initiative> optionalInitiative = initiativeRepository.findById(idInitiative);
            if (optionalInitiative.isPresent()) {
                Initiative initiativeToModify = optionalInitiative.get();

                if (initiativeToModify.getIdOrganizers().contains(email_org) &&
                        (email_org.equals(email_user) || initiativeToModify.getIdCreator().equals(email_user))) {

                    initiativeToModify.getIdMembers().remove(email_org);
                    initiativeToModify.getIdOrganizers().remove(email_org);
                    initiativeRepository.save(initiativeToModify);
                    return true;
                }
            }
        }
        return false;

    }

    @GetMapping("/initiative/getMyInitiatives/{email_user}/{token}")
    public List<Initiative> getMyInitiatives(@PathVariable(value = "email_user") String email_user,
                                             @PathVariable(value = "token") String token){

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_user + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE) {
            List<Initiative> initiatives = initiativeRepository.findAll();
            return initiatives.stream().filter(initiative -> initiative.getIdOrganizers().contains(email_user)).toList();
        }

        return new ArrayList<>();
    }

    @GetMapping("/initiative/getCreatedInitiatives/{email_user}/{token}")
    public List<Initiative> getCreatedInitiatives(@PathVariable(value = "email_user") String email_user,
                                                    @PathVariable(value = "token") String token){

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_user + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE) {
            List<Initiative> initiatives = initiativeRepository.findAll();
            return initiatives.stream().filter(initiative -> initiative.getIdCreator().equals(email_user)).toList();
        }

        return new ArrayList<>();
    }

    @GetMapping("/initiative/getMySubscribedInitiatives/{email_user}/{token}")
    public List<Initiative> getMySubscribedInitiatives(@PathVariable(value = "email_user") String email_user,
                                                       @PathVariable(value = "token") String token){

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email_user + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        ValidateCode result = restTemplate.getForObject(uri, ValidateCode.class);

        if(result == ValidateCode.ACTIVE) {
            List<Initiative> initiatives = initiativeRepository.findAll();
            return initiatives.stream().filter(initiative -> initiative.getIdMembers().contains(email_user)).toList();
        }

        return new ArrayList<>();
    }


    public void modifyInitiative(Initiative toCopy, Initiative modified){
        modified.setDescription(toCopy.getDescription());
        modified.setStartDate(toCopy.getStartDate());
        modified.setEndDate(toCopy.getEndDate());
        modified.setLocation(toCopy.getLocation());
        modified.setName(toCopy.getName());
        modified.setType(toCopy.getType());
        modified.setIdMembers(toCopy.getIdMembers());
        modified.setIdOrganizers(toCopy.getIdOrganizers());
        modified.setStatus(toCopy.getStatus());
    }

}
