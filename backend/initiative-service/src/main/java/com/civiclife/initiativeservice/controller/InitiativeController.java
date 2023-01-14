package com.civiclife.initiativeservice.controller;

import com.civiclife.initiativeservice.model.Initiative;
import com.civiclife.initiativeservice.repo.InitiativeRepository;
import com.civiclife.initiativeservice.utils.ErrorMessage;
import com.civiclife.initiativeservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/initiativeAPI/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
public class InitiativeController {

    @Autowired
    private InitiativeRepository initiativeRepository;

    @GetMapping(value = "/initiatives", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getInitiatives() {
        return initiativeRepository.findAll();
    }

    @GetMapping(value = "/initiative/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Initiative getInitiativeById(@PathVariable (value = "id") String id){
        return initiativeRepository.findById(id).orElse(null);
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @DeleteMapping(value = "/initiative/remove/{id}/{email_creator}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeInitiative(@PathVariable(value = "id") String id,
                                    @PathVariable(value = "email_creator") String email_creator,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente) {

        Optional<Initiative> optionalInitiative = initiativeRepository.findById(id);

        if(optionalInitiative.isPresent() && emailRichiedente.equals(email_creator)){
            Initiative initiative = optionalInitiative.get();
            if(initiative.getIdCreator().equals(emailRichiedente)){
                initiativeRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/initiative/subscribe/{idInitiative}/{email_user}/{emailRichiedente}")
    public boolean subscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                       @PathVariable(value = "email_user") String email_user,
                                       @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(emailRichiedente.equals(email_user)){
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if(initiative.isPresent()){
                Initiative initiativeToModify = initiative.get();
                boolean val = initiativeToModify.getIdMembers().add(email_user);
                initiativeRepository.save(initiativeToModify);
                return val;
            }
        }

        return false;

    }

    @GetMapping(value = "/initiative/unsubscribe/{idInitiative}/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean unsubscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                         @PathVariable(value = "email_user") String email_user,
                                         @PathVariable(value = "emailRichiedente") String emailRichiedente){


        if(emailRichiedente.equals(email_user)) {
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

    @GetMapping(value = "/initiative/addOrganizer/{idInitiative}/{new_org}/{email_org}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                          @PathVariable(value = "new_org") String new_org,
                                          @PathVariable(value = "email_org") String email_org,
                                          @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if (email_org.equals(emailRichiedente)) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if (initiative.isPresent()) {
                Initiative initiativeToModify = initiative.get();
                initiativeToModify.getIdMembers().add(new_org);
                boolean val = initiativeToModify.getIdOrganizers().add(new_org);
                initiativeRepository.save(initiativeToModify);
                return val;
            }
        }
        return false;
    }

    @GetMapping(value = "/initiative/removeOrganizer/{idInitiative}/{email_org}/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                             @PathVariable(value = "email_org") String email_org,
                                             @PathVariable(value = "email_user") String email_user,
                                             @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if (email_user.equals(emailRichiedente)) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if(initiative.isPresent()){
                Initiative initiativeToModify = initiative.get();
                if(initiativeToModify.getIdOrganizers().contains(email_org) &&
                        (email_org.equals(email_user) || initiativeToModify.getIdCreator().equals(email_user))){
                    initiativeToModify.getIdOrganizers().remove(email_org);
                    initiativeRepository.save(initiativeToModify);
                    return true;
                }
            }
        }
        return false;

    }

    @GetMapping(value = "/initiative/getOrganizedInitiatives/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getOrganizedInitiatives(@PathVariable(value = "email_user") String email_user,
                                             @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(email_user.equals(emailRichiedente)){
            return initiativeRepository.findInitiativesByOrganizer(email_user);
        }
        return new ArrayList<Initiative>();
    }

    @GetMapping(value = "/initiative/getCreatedInitiatives/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getCreatedInitiatives(@PathVariable(value = "email_user") String email_user,
                                                  @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(email_user.equals(emailRichiedente)){
           return initiativeRepository.findInitiativesByCreator(email_user);
        }
        return new ArrayList<Initiative>();
    }

    @GetMapping(value = "/initiative/getMySubscribedInitiatives/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getMySubscribedInitiatives(@PathVariable(value = "email_user") String email_user,
                                                       @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(email_user.equals(emailRichiedente)){
            return initiativeRepository.findInitiativeByMember(email_user);
        }

        return new ArrayList<Initiative>();
    }

    @PostMapping(value = "/initiative/create/{email_creator}/{token}/{emailRichiedente}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean createInitiative(@PathVariable(value = "email_creator") String email_creator,
                                    @PathVariable(value = "token") String token,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                    @RequestBody String initiative) {

        if (email_creator.equals(emailRichiedente)) {
            Initiative realInitiative = parseInitiative(initiative.replace("{", "").replace("}","").replace("\"", ""));
            realInitiative.setIdCreator(email_creator);
            realInitiative.setIdMembers(new HashSet<>(Collections.singleton(email_creator)));
            realInitiative.setIdOrganizers(new HashSet<>(Collections.singleton(email_creator)));
            initiativeRepository.save(realInitiative);
            return true;
        }
        return false;
    }

    @PostMapping(value = "/initiative/modify/{id}/{email_org}/{emailRichiedente}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean modifyInitiative(@RequestBody String updateInitiative,
                                    @PathVariable(value = "id") String id,
                                    @PathVariable(value = "email_org") String email_org,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente) {


        if (emailRichiedente.equals(email_org)) {
            Optional<Initiative> optionalOriginalInitiative = initiativeRepository.findById(id);
            if(optionalOriginalInitiative.isPresent()){
                Initiative originalInitiative = optionalOriginalInitiative.get();
                if(originalInitiative.getIdOrganizers().contains(email_org)) {
                    Initiative realInitiative = parseInitiative(updateInitiative.replace("{", "").replace("}","").replace("\"", ""));
                    modifyInitiative(realInitiative, originalInitiative);
                    initiativeRepository.save(originalInitiative);
                    return true;
                }
            }
        }

        return false;
    }


    private void modifyInitiative(Initiative toCopy, Initiative modified){
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

    private Initiative parseInitiative(String initiative){
        Initiative parsedInitiative = new Initiative();
        String [] campi = initiative.split(",");

        for(String campo: campi){
            String [] coppia = campo.split(":");
            String key = campo.split(":")[0];
            String value = "";

            if(coppia.length == 2){
                value = campo.split(":")[1];
            }

            switch (key){
                case "id":
                    parsedInitiative.setId(value);
                    break;
                case "name":
                    parsedInitiative.setName(value);
                    break;
                case "description":
                    parsedInitiative.setDescription(value);
                    break;
                case "startDate":
                    if(!value.equals("")){
                        parsedInitiative.setStartDate(Long.parseLong(value));
                    }
                    else{
                        parsedInitiative.setStartDate(0);
                    }
                    break;
                case "endDate":
                    if(!value.equals("")){
                        parsedInitiative.setEndDate(Long.parseLong(value));
                    }
                    else{
                        parsedInitiative.setEndDate(0);
                    }
                    break;
                case "location":
                    parsedInitiative.setLocation(value);
                    break;
                case "type":
                    parsedInitiative.setType(value);
                    break;
                case "idMembers":

                    if(value.equals("")){
                        parsedInitiative.setIdMembers(new HashSet<>());
                    }
                    else{
                        String [] members = value.split(",");
                        parsedInitiative.setIdMembers(new HashSet<>(Arrays.asList(members)));
                    }
                    break;
                case "idOrganizers":
                    if(value.equals("")){
                        parsedInitiative.setIdOrganizers(new HashSet<>());
                    }
                    else{
                        String [] members = value.split(",");
                        parsedInitiative.setIdOrganizers(new HashSet<>(Arrays.asList(members)));
                    }
                    break;
                case "idCreator":
                    parsedInitiative.setIdCreator(value);
                    break;
                case "status":
                    parsedInitiative.setStatus(value);
                    break;

            }
        }
        return parsedInitiative;
    }


}
