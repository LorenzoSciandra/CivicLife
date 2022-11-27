package com.civiclife.backend.controller;

import com.civiclife.backend.model.Utente;
import com.civiclife.backend.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
// This means URL's start with /api/v1 (after Application path)
public class UtenteController {

    @Autowired //mapping fatto direttamente da Spring
    UtenteRepository utenteRepository;

    @GetMapping("/utenti") //richiamo al metodo specifico
    public List<Utente> getAllUsers() {
        System.out.println("Getting all users.");
        List<Utente> utentes = new ArrayList<>();
        utenteRepository.findAll().forEach(utentes::add);
        // restituisce arraylist che viene tradotta in serie di oggetti json
        return utentes;
    }

    @PostMapping("/utenti/create")
    // l'oggetto viene passato come json, ma poi gonfiato nella classe
    public Utente postCUser(@RequestBody Utente utente) {
        Utente _utente = utenteRepository.save(new Utente(utente.getName(), utente.getAge()));
        return utente;
    }
}