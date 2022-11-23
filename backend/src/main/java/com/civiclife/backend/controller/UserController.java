package com.civiclife.backend.controller;

import com.civiclife.backend.model.User;
import com.civiclife.backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
// This means URL's start with /api/v1 (after Application path)
public class UserController {

    @Autowired //mapping fatto direttamente da Spring
    UserRepository userRepository;

    @GetMapping("/customers") //richiamo al metodo specifico
    public List<User> getAllCustomers() {
        System.out.println("Getting all customers.");
        List<User> customers = new ArrayList<>();
        userRepository.findAll().forEach(customers::add);
        // restituisce arraylist che viene tradotta in serie di oggetti json
        return customers;
    }

    @PostMapping("/customers/create")
    // l'oggetto viene passato come json, ma poi gonfiato nella classe
    public User postCustomer(@RequestBody User customer) {
        User _customer = userRepository.save(new User(customer.getName(), customer.getAge()));
        return customer;
    }
}