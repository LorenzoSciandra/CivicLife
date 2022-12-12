package com.civiclife.userservice.controller;

import com.civiclife.userservice.model.User;
import com.civiclife.userservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUtenti() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value = "id") String id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/user/{id}/createdInitiatives")
    public List<String> getIniziativeCreate(@PathVariable(value = "id") String id) {
        User user = getUser(id);
        if(!user.getInitiativesCreatedId().isEmpty()){
            return user.getInitiativesCreatedId();
        }
        else{
            // TODO: chiama il servizio di iniziative per ottenere le iniziative create e memorizza
            return null;
        }
    }

    @GetMapping("/user/{id}/subscribedInitiatives")
    public List<String> getIniziativeSottoscritte(@PathVariable(value = "id") String id) {
        User user = getUser(id);
        if(!user.getInitiativeSubscribedId().isEmpty()){
            return user.getInitiativeSubscribedId();
        }
        else{
            // TODO: chiama il servizio di iniziative per ottenere le iniziative sottoscritte e memorizza
            return null;
        }
    }

    @PostMapping("/user/create")
    public boolean createUser(@RequestBody User user) {
        userRepository.save(user);
        return true;
    }

    @GetMapping("/user/update/status/{idUser}")
    public boolean updateStatus(@PathVariable(value = "idUser") String idUser, @RequestBody int status) {
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setStatus(status);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @PostMapping("/user/update/email/{id}")
    public boolean updateEmail(@PathVariable(value = "id") String id, @RequestBody String email) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEmail(email);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @PostMapping("/user/update/residence/{id}")
    public boolean updateResidenza(@PathVariable(value = "id") String id, @RequestBody String residence) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setResidence(residence);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @PostMapping("/user/update/domicile/{id}")
    public boolean updateDomicilio(@PathVariable(value = "id") String id, @RequestBody String domicile) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setDomicile(domicile);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @PostMapping("/user/update/telephoneNumber/{id}")
    public boolean updateTelefono(@PathVariable(value = "id") String id, @RequestBody long telephoneNumber) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setTelephonNumber(telephoneNumber);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}