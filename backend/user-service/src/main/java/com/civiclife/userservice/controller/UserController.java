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


    @DeleteMapping("/user/delete/{emailUser}/{emailRichiedente}")
    public boolean deleteUser(@PathVariable(value = "emailUser") String emailUser,
                              @PathVariable(value = "emailRichiedente") String emailRichiedente) {
        Optional<User> optionalAdmin = userRepository.findById(emailRichiedente);
        if (optionalAdmin.isPresent()) {
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

    @PostMapping("/user/create")
    public boolean createUser(@RequestBody User user) {
        userRepository.save(user);
        return true;
    }


    @PostMapping("/user/update/status/{emailUser}/{emailRichiedente}")
    public boolean updateStatus(@PathVariable(value = "emailUser") String emailUser,
                                @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                @RequestBody int new_status) {


        Optional<User> optionalAdmin = userRepository.findById(emailRichiedente);
        if (optionalAdmin.isPresent()) {
            User admin = optionalAdmin.get();
            if (admin.isAdmin()) {
                Optional<User> optionalUser = userRepository.findById(emailUser);
                if (optionalUser.isPresent()) {
                    if (new_status == 2) {
                        userRepository.deleteById(emailUser);
                    } else {
                        User user = optionalUser.get();
                        user.setStatus(new_status);
                        userRepository.save(user);
                    }
                    return true;
                }
            }
        }


        return false;
    }

    @PostMapping("/user/update/residence/{email}/{emailRichiedente}")
    public boolean updateResidenza(@PathVariable(value = "email") String email,
                                   @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                   @RequestBody String residence) {


        Optional<User> optionalUser = userRepository.findById(email);
        //System.out.println("Mi hanno chiesto di cambiare email: " + email + "con richiedente: " + emailRichiedente);

        if (optionalUser.isPresent() && email.equals(emailRichiedente)) {
            User user = optionalUser.get();
            user.setResidence(residence);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @PostMapping("/user/update/domicile/{email}/{emailRichiedente}")
    public boolean updateDomicilio(@PathVariable(value = "email") String email,
                                   @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                   @RequestBody String domicile) {


        Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isPresent() && email.equals(emailRichiedente)) {
            User user = optionalUser.get();
            user.setDomicile(domicile);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @PostMapping("/user/update/telephoneNumber/{email}/{emailRichiedente}")
    public boolean updateTelefono(@PathVariable(value = "email") String email,
                                  @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                  @RequestBody long telephoneNumber) {


        Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isPresent() && email.equals(emailRichiedente)) {
            User user = optionalUser.get();
            user.setTelephonNumber(telephoneNumber);
            userRepository.save(user);
            return true;
        }
        return false;

    }

    @PostMapping("/user/update/birthDayDate/{email}/{emailRichiedente}")
    public boolean updateBirthDayDate(@PathVariable(value = "email") String email,
                                      @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                      @RequestBody long birthDayDate) {


        Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isPresent() && email.equals(emailRichiedente)) {
            User user = optionalUser.get();
            user.setBirthDate(birthDayDate);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @PostMapping("/user/update/fiscalCode/{email}/{emailRichiedente}")
    public boolean updateFiscalCode(@PathVariable(value = "email") String email,
                                    @PathVariable(value = "emailRichiedente") String emailRichiedente,
                                    @RequestBody String fiscalCode) {


        Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isPresent() && email.equals(emailRichiedente)) {
            User user = optionalUser.get();
            user.setFiscalCode(fiscalCode);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}