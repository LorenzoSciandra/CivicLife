package com.civiclife.userservice.controller;

import com.civiclife.userservice.model.User;
import com.civiclife.userservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/userAPI/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUtenti() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{email}")
    public User getUser(@PathVariable(value = "email") String email) {
        return userRepository.findById(email).orElse(null);
    }


    @DeleteMapping("/user/delete/{email}")
    public boolean deleteUser(@PathVariable(value = "email") String email) {
        userRepository.deleteById(email);
        return true;
    }

    @PostMapping("/user/create")
    public boolean createUser(@RequestBody User user) {
        userRepository.save(user);
        return true;
    }

    @GetMapping("/user/createFromLogin/{email}/{name}/{surname})")
    public boolean createFromLogin(@PathVariable(value = "email") String email, @PathVariable(value = "name") String name, @PathVariable(value = "surname") String surname) {
        Optional<User> optionalUser = userRepository.findById(email);

        if(optionalUser.isEmpty()){
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setSurname(surname);
            user.setAdmin(false);
            user.setBirthDate(0);
            user.setDomicile("");
            user.setFiscalCode("");
            user.setResidence("");
            user.setStatus(0);
            user.setTelephonNumber(0);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    @GetMapping("/user/update/status/{emailAdmin}/{emailUser}/{token}")
    public boolean updateStatus(@PathVariable(value = "emailUser") String emailUser,
                                @PathVariable(value = "emailAdmin") String emailAdmin,
                                @PathVariable(value = "token") String token,
                                @RequestBody int status) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + emailAdmin + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

        if(result) {
            Optional<User> optionalAdmin = userRepository.findById(emailAdmin);

            if(optionalAdmin.isPresent()){
                User admin = optionalAdmin.get();

                if(admin.isAdmin()){
                    Optional<User> optionalUser = userRepository.findById(emailUser);
                    if (optionalUser.isPresent()) {

                        if(status == 2){
                            userRepository.deleteById(emailUser);
                        }else {
                            User user = optionalUser.get();
                            user.setStatus(status);
                            userRepository.save(user);
                        }
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @PostMapping("/user/update/residence/{email}/{token}")
    public boolean updateResidenza(@PathVariable(value = "email") String email,
                                   @PathVariable(value = "token") String token,
                                   @RequestBody String residence) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

        if(result) {
            Optional<User> optionalUser = userRepository.findById(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setResidence(residence);
                userRepository.save(user);
                return true;
            }
            return false;
        }
        return false;
    }

    @PostMapping("/user/update/domicile/{email}/{token}")
    public boolean updateDomicilio(@PathVariable(value = "email") String email,
                                   @PathVariable(value = "token") String token,
                                   @RequestBody String domicile) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

        if(result) {

            Optional<User> optionalUser = userRepository.findById(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setDomicile(domicile);
                userRepository.save(user);
                return true;
            }
            return false;
        }
        return false;
    }

    @PostMapping("/user/update/telephoneNumber/{email}/{token}")
    public boolean updateTelefono(@PathVariable(value = "email") String email,
                                  @PathVariable(value = "token") String token,
                                  @RequestBody long telephoneNumber) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

        if(result) {

            Optional<User> optionalUser = userRepository.findById(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setTelephonNumber(telephoneNumber);
                userRepository.save(user);
                return true;
            }
            return false;
        }
        return false;
    }

    @PostMapping("/user/update/birthDayDate/{email}/{token}")
    public boolean updateBirthDayDate(@PathVariable(value = "email") String email,
                                  @PathVariable(value = "token") String token,
                                  @RequestBody long birthDayDate) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

        if(result) {

            Optional<User> optionalUser = userRepository.findById(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setBirthDate(birthDayDate);
                userRepository.save(user);
                return true;
            }
            return false;
        }
        return false;
    }

    @PostMapping("/user/update/fiscalCode/{email}/{token}")
    public boolean updateFiscalCode(@PathVariable(value = "email") String email,
                                      @PathVariable(value = "token") String token,
                                      @RequestBody String fiscalCode) {

        String uri = "http://localhost:8080/authAPI/v1/validate/" + email + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, boolean.class));

        if(result) {

            Optional<User> optionalUser = userRepository.findById(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setFiscalCode(fiscalCode);
                userRepository.save(user);
                return true;
            }
            return false;
        }
        return false;
    }
}