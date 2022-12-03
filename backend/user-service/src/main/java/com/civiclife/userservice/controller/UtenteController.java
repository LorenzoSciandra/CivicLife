package com.civiclife.userservice.controller;

import com.civiclife.userservice.model.Utente;
import com.civiclife.userservice.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class UtenteController {

    @Autowired
    UtenteRepository utenteRepository;

    @GetMapping("/utenti")
    public List<Utente> getAllUtenti() {
        List<Utente> utenti = new ArrayList<>();
        utenteRepository.findAll().forEach(utenti::add);
        return utenti;
    }

    @GetMapping("/utenti/{id}")
    public Utente getUtente(@PathVariable(value = "id") long id) {
        return utenteRepository.findById(id).orElse(null);
    }

    @PostMapping("/utenti/create")
    public Utente createUtente(@RequestBody Utente utente) {
        return utenteRepository.save(utente);
    }

    @PostMapping("/utenti/update/status/{idUser}&{idAdmin}")
    public Utente updateStatus(@PathVariable(value = "idUser") long idUser, @PathVariable(value = "idAdmin") long idAdmin, @RequestBody int stato) {
        Utente admin = utenteRepository.findById(idAdmin).orElse(null);
        if(admin!= null && admin.getAmministratore()) {
            Utente utente = utenteRepository.findById(idUser).orElse(null);
            if(utente != null) {
                utente.setStato(stato);
                return utenteRepository.save(utente);
            }
            else {
                System.out.println("L'utente non esiste");
            }
        }
        else{
            System.out.println("Non sei admin");
        }
        return null;
    }

    @PostMapping("/utenti/update/email/{id}")
    public Utente updateEmail(@PathVariable(value = "id") long id, @RequestBody String email) {
        Utente utente = utenteRepository.findById(id).orElse(null);
        if(utente != null ) {
            utente.setEmail(email);
            return utenteRepository.save(utente);
        }
        else {
            System.out.println("L'utente non esiste");
        }
        return null;
    }

    @PostMapping("/utenti/update/residenza/{id}")
    public Utente updateResidenza(@PathVariable(value = "id") long id, @RequestBody String residenza) {
        Utente utente = utenteRepository.findById(id).orElse(null);
        if(utente != null ) {
            utente.setResidenza(residenza);
            return utenteRepository.save(utente);
        }
        else {
            System.out.println("L'utente non esiste");
        }
        return null;
    }

    @PostMapping("/utenti/update/domicilio/{id}")
    public Utente updateDomicilio(@PathVariable(value = "id") long id, @RequestBody String domicilio) {
        Utente utente = utenteRepository.findById(id).orElse(null);
        if(utente != null ) {
            utente.setDomicilio(domicilio);
            return utenteRepository.save(utente);
        }
        else {
            System.out.println("L'utente non esiste");
        }
        return null;
    }

    @PostMapping("/utenti/update/telefono/{id}")
    public Utente updateTelefono(@PathVariable(value = "id") long id, @RequestBody long telefono) {
        Utente utente = utenteRepository.findById(id).orElse(null);
        if(utente != null ) {
            utente.setTelefono(telefono);
            return utenteRepository.save(utente);
        }
        else {
            System.out.println("L'utente non esiste");
        }
        return null;
    }
}
