package com.civiclife.initiativeservice.controller;

import com.civiclife.initiativeservice.model.Initiative;
import com.civiclife.initiativeservice.model.InitiativeReadOnly;
import com.civiclife.initiativeservice.repo.InitiativeRepository;
import com.civiclife.initiativeservice.service.ApiCall;
import com.civiclife.initiativeservice.utils.ErrorMessage;
import com.civiclife.initiativeservice.utils.UserStatus;
import com.civiclife.initiativeservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/initiativeAPI/v1")
public class InitiativeController {

    @Autowired
    private ApiCall apiCall;

    @Autowired
    private InitiativeRepository initiativeRepository;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value="/getAllNamesDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<InitiativeReadOnly> getAllInitiativesNamesAndDescriptions() {
        List<Initiative> initiatives = initiativeRepository.findAll();
        Set<InitiativeReadOnly> initiativesReadOnly = new HashSet<>();
        for(Initiative initiative : initiatives){
            InitiativeReadOnly initiativeReadOnly = new InitiativeReadOnly();
            initiativeReadOnly.setName(initiative.getName());
            initiativeReadOnly.setDescription(initiative.getDescription());
            initiativeReadOnly.setLocation(initiative.getLocation());
            initiativeReadOnly.setType(initiative.getType());
            initiativesReadOnly.add(initiativeReadOnly);
        }

        return initiativesReadOnly;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiatives/{email}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getInitiatives(@PathVariable String email, @PathVariable String emailRichiedente) {

        if (email.equals(emailRichiedente)) {
            UserStatus userStatus = apiCall.getUserStatus(email);
            if(userStatus!=null && userStatus != UserStatus.BANNED){
                return initiativeRepository.findAll();
            }
        }
        return new ArrayList<>();
    }
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/remove/{id}/{email_creator}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeInitiative(@PathVariable(value = "id") String id,
                                    @PathVariable(value = "email_creator") String email_creator,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente) {

        Optional<Initiative> optionalInitiative = initiativeRepository.findById(id);
        if(optionalInitiative.isPresent() && emailRichiedente.equals(email_creator)){
            UserStatus userStatus = apiCall.getUserStatus(email_creator);
            if(userStatus!=null && userStatus != UserStatus.BANNED){
                Initiative initiative = optionalInitiative.get();
                if(initiative.getIdCreator().equals(emailRichiedente)){
                    initiativeRepository.deleteById(id);
                    return true;
                }
            }
        }
        return false;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/subscribe/{idInitiative}/{email_user}/{emailRichiedente}")
    public boolean subscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                       @PathVariable(value = "email_user") String email_user,
                                       @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(emailRichiedente.equals(email_user)){
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if(initiative.isPresent()){
                Initiative initiativeToModify = initiative.get();
                if(checkDateForOpOnInitative(initiativeToModify.getStartDate(), initiativeToModify.getEndDate())) {
                    UserStatus userStatus = apiCall.getUserStatus(email_user);
                    if (userStatus != null && userStatus != UserStatus.BANNED) {

                        boolean val = initiativeToModify.getIdMembers().add(email_user);
                        if (val) {
                            initiativeRepository.save(initiativeToModify);
                        }
                        return val;
                    }
                }
            }
        }

        return false;

    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/unsubscribe/{idInitiative}/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean unsubscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                         @PathVariable(value = "email_user") String email_user,
                                         @PathVariable(value = "emailRichiedente") String emailRichiedente){


        if(emailRichiedente.equals(email_user)) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if (initiative.isPresent()) {
                Initiative initiativeToModify = initiative.get();
                if(checkDateForOpOnInitative(initiativeToModify.getStartDate(), initiativeToModify.getEndDate())) {
                    UserStatus userStatus = apiCall.getUserStatus(email_user);
                    if(userStatus!=null && userStatus != UserStatus.BANNED){
                        boolean val = initiativeToModify.getIdMembers().remove(email_user);
                        if(val){
                            initiativeRepository.save(initiativeToModify);
                        }
                        return val;
                    }
                }
            }
        }
        return false;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @PostMapping(value= "/initiative/changeOrganizers/{idInitiative}/{emailUser}/{emailRichiedente}",
            consumes=MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public boolean changeOrganizers(@PathVariable(value = "idInitiative") String idInitiative,
                                    @PathVariable(value = "emailUser") String emailUser,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                    @RequestBody String newOrgs){

        if (emailUser.equals(emailRichiedente)) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if(initiative.isPresent()){
                Initiative initiativeToModify = initiative.get();
                Set<String> newOrganizers = parseOrganizers(newOrgs, initiativeToModify.getIdCreator());
                if(checkDateForOpOnInitative(initiativeToModify.getStartDate(), initiativeToModify.getEndDate())){
                    if(initiativeToModify.getIdCreator().equals(emailRichiedente)) {
                        UserStatus userStatus = apiCall.getUserStatus(emailUser);
                        if(userStatus == UserStatus.ACTIVE) {
                            initiativeToModify.setIdOrganizers(newOrganizers);
                            initiativeRepository.save(initiativeToModify);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/addOrganizer/{idInitiative}/{new_org}/{email_org}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                          @PathVariable(value = "new_org") String new_org,
                                          @PathVariable(value = "email_org") String email_org,
                                          @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if (email_org.equals(emailRichiedente)) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if (initiative.isPresent()) {
                UserStatus userStatus = apiCall.getUserStatus(email_org);
                if(userStatus == UserStatus.ACTIVE) {
                    Initiative initiativeToModify = initiative.get();
                    if(checkDateForOpOnInitative(initiativeToModify.getStartDate(), initiativeToModify.getEndDate()) &&
                            initiativeToModify.getIdCreator().equals(emailRichiedente)){
                        boolean addMem = initiativeToModify.getIdMembers().add(new_org);
                        boolean val = initiativeToModify.getIdOrganizers().add(new_org);
                        if(val || addMem){
                            initiativeRepository.save(initiativeToModify);
                        }
                        return val;
                    }
                }
            }
        }
        return false;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/removeOrganizer/{idInitiative}/{email_org}/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative,
                                             @PathVariable(value = "email_org") String email_org,
                                             @PathVariable(value = "email_user") String email_user,
                                             @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if (email_user.equals(emailRichiedente)) {
            Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
            if(initiative.isPresent()){
                UserStatus userStatus = apiCall.getUserStatus(email_user);
                if(userStatus == UserStatus.ACTIVE) {
                    Initiative initiativeToModify = initiative.get();
                    if(checkDateForOpOnInitative(initiativeToModify.getStartDate(), initiativeToModify.getEndDate())){
                        if (email_org.equals(email_user) || initiativeToModify.getIdCreator().equals(emailRichiedente)) {
                            boolean val = initiativeToModify.getIdOrganizers().remove(email_org);
                            if (val) {
                                initiativeRepository.save(initiativeToModify);
                            }
                            return val;
                        }
                    }
                }
            }
        }
        return false;

    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value="initiative/getInitiative/{idInitiative}/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Initiative getInitiative(@PathVariable(value = "idInitiative") String idInitiative, @PathVariable(value = "email_user") String email_user,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente){
        if(emailRichiedente.equals(email_user)){
            UserStatus userStatus = apiCall.getUserStatus(email_user);
            if(userStatus!=null && userStatus != UserStatus.BANNED){
                Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
                if(initiative.isPresent()){
                    return initiative.get();
                }
            }
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/getOrganizedInitiatives/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getOrganizedInitiatives(@PathVariable(value = "email_user") String email_user,
                                                    @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(email_user.equals(emailRichiedente)){
            UserStatus userStatus = apiCall.getUserStatus(email_user);
            if(userStatus!=null && userStatus != UserStatus.BANNED){
                return initiativeRepository.findInitiativesByOrganizer(email_user);
            }
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/getCreatedInitiatives/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getCreatedInitiatives(@PathVariable(value = "email_user") String email_user,
                                                  @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(email_user.equals(emailRichiedente)){
            UserStatus userStatus = apiCall.getUserStatus(email_user);
            if(userStatus != null && userStatus != UserStatus.BANNED){
                return initiativeRepository.findInitiativesByCreator(email_user);
            }
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/initiative/getMySubscribedInitiatives/{email_user}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Initiative> getMySubscribedInitiatives(@PathVariable(value = "email_user") String email_user,
                                                       @PathVariable(value = "emailRichiedente") String emailRichiedente){

        if(email_user.equals(emailRichiedente)){
            UserStatus userStatus = apiCall.getUserStatus(email_user);
            if(userStatus!=null && userStatus != UserStatus.BANNED){
                return initiativeRepository.findInitiativeByMember(email_user);
            }
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @PostMapping(value = "/initiative/create/{email_creator}/{emailRichiedente}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean createInitiative(@PathVariable(value = "email_creator") String email_creator,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                    @RequestBody String initiative) {

        if (email_creator.equals(emailRichiedente)) {
            UserStatus userStatus = apiCall.getUserStatus(email_creator);
            if (userStatus == UserStatus.ACTIVE) {
                Initiative realInitiative = parseInitiative(initiative.replace("{", "").replace("}", "").replace("\"", ""));
                if(checkDatesInitiative(realInitiative.getStartDate(), realInitiative.getEndDate())){
                    realInitiative.setIdCreator(email_creator);
                    realInitiative.setIdMembers(new HashSet<>(Collections.singleton(email_creator)));
                    realInitiative.setIdOrganizers(new HashSet<>(Collections.singleton(email_creator)));
                    initiativeRepository.save(realInitiative);
                    return true;
                }
            }
        }
        return false;
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @PostMapping(value = "/initiative/modify/{id}/{email_org}/{emailRichiedente}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean modifyInitiative(@RequestBody String updateInitiative,
                                    @PathVariable(value = "id") String id,
                                    @PathVariable(value = "email_org") String email_org,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente) {

        // System.out.println("Iniziative che mi è arrivata: " + updateInitiative);

        if (emailRichiedente.equals(email_org)) {
            Optional<Initiative> optionalOriginalInitiative = initiativeRepository.findById(id);
            if(optionalOriginalInitiative.isPresent()){
                UserStatus userStatus = apiCall.getUserStatus(email_org);
                if(userStatus == UserStatus.ACTIVE ) {
                    Initiative originalInitiative = optionalOriginalInitiative.get();;
                    if ((originalInitiative.getIdOrganizers().contains(emailRichiedente) ||
                            originalInitiative.getIdCreator().equals(emailRichiedente)) &&
                            checkDateForOpOnInitative(originalInitiative.getStartDate(), originalInitiative.getEndDate())) {
                        Initiative newInitiative = parseInitiative(updateInitiative.replace("{", "").replace("}", "").replace("\"", ""));
                        //System.out.println("Iniziative che mi è arrivata dopo il parsing: " + newInitiative);
                        boolean update = modifyInitiative(newInitiative, originalInitiative);
                        if(update){
                            initiativeRepository.save(originalInitiative);
                        }
                        return update;
                    }
                }
            }
        }

        return false;
    }


    private boolean modifyInitiative(Initiative toCopy, Initiative modified){
        modified.setDescription(toCopy.getDescription());
        modified.setStartDate(toCopy.getStartDate());
        modified.setEndDate(toCopy.getEndDate());
        modified.setLocation(toCopy.getLocation());
        modified.setName(toCopy.getName());
        modified.setType(toCopy.getType());
        return true;
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
                    parsedInitiative.setType(parseType(value));
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

            }
        }
        return parsedInitiative;
    }

    private Initiative.InitiativeType parseType(String type){
        return switch (type) {
            case "SOCIAL" -> Initiative.InitiativeType.SOCIAL;
            case "SPORT" -> Initiative.InitiativeType.SPORT;
            case "CULTURE" -> Initiative.InitiativeType.EDUCATIONAL;
            case "ENVIRONMENT" -> Initiative.InitiativeType.ENVIRONMENTAL;
            case "FOOD" -> Initiative.InitiativeType.FOOD;
            case "HEALTH" -> Initiative.InitiativeType.HEALTH;
            default -> Initiative.InitiativeType.OTHER;
        };
    }

    // non posso creare un'iniziativa che inizi nel passato e che finisca prima che inizi
    private boolean checkDatesInitiative(long startDate, long endDate){
        long now = System.currentTimeMillis()/1000;
        return endDate >= startDate &&  startDate >= now;
    }

    // per fare operazione su iniziativa questa non deve essere finita
    // con "operazione" rientra: modifica, subscribe, unsubscribe, etc.
    private boolean checkDateForOpOnInitative(long startDate, long endDate){
        long now = System.currentTimeMillis()/1000;
        return startDate <= endDate && now <= endDate;
    }

    private Set<String> parseOrganizers(String organizers, String creator){
        String first = organizers.replace("[", "").replace("]", "").replace("\"", "");
        String[] organizersArray = first.split(",");
        if(organizersArray.length == 1 && organizersArray[0].equals("")){
            return new HashSet<>(Collections.singleton(creator));
        }
        return new HashSet<>(Arrays.asList(organizersArray));
    }

    // POSTMAN TEST: ADD AND DELETE INITIATIVES

    @PostMapping(value = "/postman/create")
    public boolean createInitiativePostman(@RequestBody Initiative[] initiatives){
        initiativeRepository.saveAll(Arrays.asList(initiatives));
        return true;
    }

    @DeleteMapping(value = "/postman/delete/{id}")
    public boolean deleteInitiativePostman(@PathVariable String id){
        Optional<Initiative> optionalInitiative = initiativeRepository.findById(id);
        if(optionalInitiative.isPresent()){
            initiativeRepository.delete(optionalInitiative.get());
            return true;
        }
        return false;
    }

    @GetMapping(value = "/postman/getAll")
    public List<Initiative> getAllInitiativesPostman(){
        return initiativeRepository.findAll();
    }

}



