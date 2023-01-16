package com.civiclife.userservice.controller;

import com.civiclife.userservice.model.StatusType;
import com.civiclife.userservice.model.User;
import com.civiclife.userservice.repo.UserRepository;
import com.civiclife.userservice.utils.ErrorMessage;
import com.civiclife.userservice.utils.ValidateCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/userAPI/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
public class UserController {

    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper;

    //only for testing from postman
    @GetMapping("/postman/setAdmin/{email}")
    public boolean setAdmin(@PathVariable String email){
        Optional<User> optionalUser = userRepository.findById(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setAdmin(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @GetMapping("/users/{email}/{emailRichiedente}")
    public List<User> getAllUtenti(@PathVariable(value = "email") String email,
                                   @PathVariable(value = "emailRichiedente") String emailRichiedente) {

        Optional<User> optionalUser = userRepository.findById(email);
        if(optionalUser.isPresent() && emailRichiedente.equals(email)){
            User user = optionalUser.get();
            if(user.isAdmin()){
                return userRepository.findAll();
            }
        }
        return new ArrayList<>();
    }

    @GetMapping(value = "/user/get/{email}/{emailRichiedente}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable(value = "email") String email,
                        @PathVariable(value = "emailRichiedente") String emailRichiedente) {

        Optional<User> optionalUser = userRepository.findById(email);
        Optional<User> possiblyAdmin = userRepository.findById(emailRichiedente);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(possiblyAdmin.isPresent()){
                User admin = possiblyAdmin.get();
                if(admin.isAdmin() || email.equals(emailRichiedente)){
                    return user;
                }
            }
        }
        return null;
    }

    @GetMapping(value = "/user/isAdmin/{email}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isAdmin(@PathVariable(value = "email") String email,
                           @PathVariable(value = "emailRichiedente") String emailRichiedente) {

        Optional<User> possiblyAdmin = userRepository.findById(emailRichiedente);
        Optional<User> requestedUser = userRepository.findById(email);
        if(possiblyAdmin.isPresent() && requestedUser.isPresent()){
            User admin = possiblyAdmin.get();
            User requested = requestedUser.get();
            if(admin.isAdmin() || email.equals(emailRichiedente)) {
                return requested.isAdmin();
            }
        }
        return false;
    }

    @GetMapping(value = "/user/authorizeBonus/{email}/{emailRichiedente}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean authorizeBonus(@PathVariable(value = "email") String email,
                        @PathVariable(value = "emailRichiedente") String emailRichiedente) {
        if(email.equals(emailRichiedente)){
            Optional<User> optionalUser = userRepository.findById(email);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                user.setAuthorizeBonus(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/user/authorizeVaccine/{email}/{emailRichiedente}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean authorizeVaccine(@PathVariable(value = "email") String email,
                        @PathVariable(value = "emailRichiedente") String emailRichiedente) {
        if(email.equals(emailRichiedente)){
            Optional<User> optionalUser = userRepository.findById(email);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                user.setAuthorizeVaccine(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @DeleteMapping("/user/delete/{email}/{emailUser}/{emailRichiedente}")
    public boolean deleteUser(@PathVariable(value = "email") String email,
                              @PathVariable(value = "emailUser") String emailUser,
                              @PathVariable(value = "emailRichiedente") String emailRichiedente) {
        Optional<User> optionalAdmin = userRepository.findById(email);

        if (optionalAdmin.isPresent() && email.equals(emailRichiedente)) {
            User admin = optionalAdmin.get();
            if (admin.isAdmin()) {
                Optional<User> optionalUser = userRepository.findById(emailUser);
                if (optionalUser.isPresent()) {
                    userRepository.deleteById(emailUser);
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(  @PathVariable(value = "code") ValidateCode code,
                                @PathVariable(value = "path") String path,
                                @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    //only for testing with postman
    @PostMapping("/postman/create")
    public boolean createUser(@RequestBody User user) {
        userRepository.save(user);
        return true;
    }

    @PostMapping(value = "/user/update/{email}/{emailRichiedente}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateUser(@PathVariable(value = "email") String email,
                              @PathVariable(value = "emailRichiedente") String emailRichiedente,
                              @RequestBody String user){

        if(email.equals(emailRichiedente)){
            User new_user = parseUser(user.replace("{", "").replace("}","").replace("\"", ""));
            return updateUser(new_user);
        }
        return false;
    }

    public boolean updateUser(User user) {
        System.out.println("Modifico utente come: " + user);
        Optional<User> optionalUser = userRepository.findById(user.getEmail());
        if(optionalUser.isPresent()){
            User userToUpdate = optionalUser.get();
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userToUpdate.setDomicile(user.getDomicile());
            userToUpdate.setResidence(user.getResidence());
            userToUpdate.setTelephonNumber(user.getTelephonNumber());
            userToUpdate.setFiscalCode(user.getFiscalCode());
            userToUpdate.setBirthDate(user.getBirthDate());
            userToUpdate.setAuthorizeVaccine(user.isAuthorizeVaccine());
            userToUpdate.setAuthorizeBonus(user.isAuthorizeBonus());
            userRepository.save(userToUpdate);
            return true;
        }
        return false;
    }

    private User parseUser(String new_user){
        User user = new User();
        String [] campi = new_user.split(",");
        for (String campo: campi){
            String [] coppia = campo.split(":");
            String key = campo.split(":")[0];
            String value = "";

            if(coppia.length == 2){
                value = campo.split(":")[1];
            }

            switch (key) {
                case "email" -> user.setEmail(value);
                case "name" -> user.setName(value);
                case "surname" -> user.setSurname(value);
                case "admin" -> {
                    if (value.equals("")){
                        user.setAdmin(false);
                    }
                    else {
                        user.setAdmin(Boolean.parseBoolean(value));
                    }
                }
                case "fiscalCode" -> user.setFiscalCode(value);
                case "residence" -> user.setResidence(value);
                case "domicile" -> user.setDomicile(value);
                case "birthDate" -> {
                    if(value.equals("")){
                        user.setBirthDate(0);
                    }
                    else {
                        user.setBirthDate(Long.parseLong(value));
                    }
                }
                case "status" -> {
                    if(value.equals("")){
                        user.setStatus(StatusType.ACTIVE);
                    }
                    else {
                        user.setStatus(StatusType.valueOf(value));
                    }
                }
                case "telephonNumber" -> {
                    if(value.equals("")){
                        user.setTelephonNumber(0);
                    }
                    else {
                        user.setTelephonNumber(Long.parseLong(value));
                    }
                }
                case "authorizeBonus" -> {
                    if(value.equals("")){
                        user.setAuthorizeBonus(false);
                    }
                    else {
                        user.setAuthorizeBonus(Boolean.parseBoolean(value));
                    }
                }
                case "authorizeVaccine" -> {
                    if(value.equals("")){
                        user.setAuthorizeVaccine(false);
                    }
                    else {
                        user.setAuthorizeVaccine(Boolean.parseBoolean(value));
                    }
                }
            }
        }
        return user;
    }


}