package com.civiclife.initiativeservice.controller;

import com.civiclife.initiativeservice.model.Initiative;
import com.civiclife.initiativeservice.repo.InitiativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class InitiativeController {

    @Autowired
    private InitiativeRepository initiativeRepository;

    @GetMapping("/initiatives")
    public List<Initiative> getInitiatives() {
        return initiativeRepository.findAll();
    }

    @GetMapping("/initiative/{id}")
    public Optional<Initiative> getInitiativeById(@PathVariable (value = "id") String id){
        return initiativeRepository.findById(id);
    }

    @GetMapping("/initiative/remove/{id}")
    //TODO: controllare che l'utente sia il creatore
    public boolean removeInitiative(@PathVariable(value = "id") String id) {
        System.out.println("Sto cancellando l'iniziativa: " + id);
        initiativeRepository.deleteById(id);
        return true;
    }

    @PostMapping("/initiative/create")
    public boolean createInitiative(@RequestBody Initiative initiative) {
        initiativeRepository.save(initiative);
        System.out.println("Sto creando l'iniziativa: " + initiative.getName());
        return true;
    }

    @PostMapping("/initiative/modify/{id}")
    // TODO: controllare che l'utente sia un organizzatore
    public boolean modifyInitiative(@RequestBody Initiative updateInitiative, @PathVariable(value = "id") String id){
        Optional<Initiative> optionalOriginalInitiative = initiativeRepository.findById(id);

        if(optionalOriginalInitiative.isPresent()){
            Initiative originalInitiative = optionalOriginalInitiative.get();
            initiativeRepository.deleteById(id);
            initiativeRepository.save(modifyInitiative(originalInitiative, updateInitiative));
            return true;

        }
        return false;

    }

    @GetMapping("/initiative/{idInitiative}/subscribe")
    public boolean subscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative, @RequestBody String idUser){
        Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
        if(initiative.isPresent()){
            Initiative intiativeToModify = initiative.get();
            if(!intiativeToModify.getIdMembers().contains(idUser)){
                intiativeToModify.getIdMembers().add(idUser);
                initiativeRepository.save(intiativeToModify);
                System.out.println("Sto iscrivendo l'utente: " + idUser + " all'iniziativa: " + initiative.get().getName());
                return true;
            }
        }
        return false;

    }

    @GetMapping("/initiative/{idInitiative}/unsubscribe")
    public boolean unsubscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative, @RequestBody String idUser){
        Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
        if(initiative.isPresent()){
            Initiative initiativeToModify = initiative.get();

            if(initiativeToModify.getIdMembers().contains(idUser)){
                initiativeToModify.getIdMembers().remove(idUser);
                initiativeRepository.save(initiativeToModify);
                System.out.println("Sto disiscrivendo l'utente: " + idUser + " all'iniziativa: " + initiative.get().getName());
                return true;
            }
        }
        return false;

    }

    @GetMapping("/initiative/{idInitiative}/addOrganizer")
    // TODO: controllo che l'utente sia il creatore dell'iniziativa
    public boolean addOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative, @RequestBody String idUser){
        Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
        if(initiative.isPresent()){
            Initiative intiativeToModify = initiative.get();

            if(!intiativeToModify.getIdOrganizers().contains(idUser)){
                intiativeToModify.getIdOrganizers().add(idUser);
                initiativeRepository.save(intiativeToModify);
                System.out.println("Sto aggiungendo l'utente: " + idUser + " come organizzatore all'iniziativa: " + initiative.get().getName());
                return true;
            }
        }
        return false;

    }

    @GetMapping("/initiative/{idInitiative}/removeOrganizer")
    //TODO: controllare che l'utente sia creatore dell'iniziativa
    public boolean removeOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative, @RequestBody String idUser){
        Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
        if(initiative.isPresent()){
            Initiative intiativeToModify = initiative.get();

            if(intiativeToModify.getIdOrganizers().contains(idUser)){
                intiativeToModify.getIdOrganizers().remove(idUser);
                initiativeRepository.save(intiativeToModify);
                System.out.println("Sto rimuovendo l'utente: " + idUser + " come organizzatore all'iniziativa: " + initiative.get().getName());
                return true;
            }
        }
        return false;

    }

    @GetMapping("/initiative/getMyInitiatives/{idUtente}")
    public List<Initiative> getMyInitiatives(@PathVariable(value = "idUtente") String idUtente){
        List<Initiative> initiatives = initiativeRepository.findAll();
        List<Initiative> createInitives = initiatives.stream().filter(initiative -> initiative.getIdCreator().equals(idUtente)).collect(Collectors.toList());
        List<Initiative> organizerInitives = initiatives.stream().filter(initiative -> initiative.getIdOrganizers().contains(idUtente)).toList();
        createInitives.addAll(organizerInitives);
        return createInitives;
    }

    @GetMapping("/initiative/getMySubscribedInitiatives/{idUtente}")
    public List<Initiative> getMySubscribedInitiatives(@PathVariable(value = "idUtente") String idUtente){
        List<Initiative> initiatives = initiativeRepository.findAll();
        return initiatives.stream().filter(initiative -> initiative.getIdMembers().contains(idUtente)).toList();
    }


    public Initiative modifyInitiative(Initiative original, Initiative modified){
        Initiative result = new Initiative();

        result.setId(original.getId());
        result.setDescription(modified.getDescription());
        result.setStartDate(modified.getStartDate());
        result.setEndDate(modified.getEndDate());
        result.setLocation(modified.getLocation());
        result.setName(modified.getName());
        result.setType(modified.getType());
        result.setIdCreator(original.getIdCreator());
        result.setIdMembers(original.getIdMembers());
        result.setIdOrganizers(original.getIdOrganizers());
        result.setStatus(original.getStatus());

        return result;
    }

}
