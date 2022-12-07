package com.civiclife.initiativeservice.controller;

import com.civiclife.initiativeservice.model.Initiative;
import com.civiclife.initiativeservice.repo.InitiativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/initiative/{id}/remove")
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

    @PostMapping("/initiative/{id}/modify")
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

    @GetMapping("/initiative/{idInitiative}/subscribe/{idUser}")
    public boolean subscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative, @PathVariable(value = "idUser") int idUser){
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

    @GetMapping("/initiative/{idInitiative}/unsubscribe/{idUser}")
    public boolean unsubscribeInitiative(@PathVariable(value = "idInitiative") String idInitiative, @PathVariable(value = "idUser") int idUser){
        Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
        if(initiative.isPresent()){
            Initiative initiativeToModify = initiative.get();

            if(initiativeToModify.getIdMembers().contains(idUser)){
                initiativeToModify.getIdMembers().remove((Integer) idUser);
                initiativeRepository.save(initiativeToModify);
                System.out.println("Sto disiscrivendo l'utente: " + idUser + " all'iniziativa: " + initiative.get().getName());
                return true;
            }
        }
        return false;

    }

    @GetMapping("/initiative/{idInitiative}/addOrganizer/{idUser}")
    // TODO: controllo che l'utente sia il creatore dell'iniziativa
    public boolean addOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative, @PathVariable(value = "idUser") int idUser){
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

    @GetMapping("/initiative/{idInitiative}/removeOrganizer/{idUser}")
    //TODO: controllare che l'utente sia creatore dell'iniziativa
    public boolean removeOrganizerInitiative(@PathVariable(value = "idInitiative") String idInitiative, @PathVariable(value = "idUser") int idUser){
        Optional<Initiative> initiative = initiativeRepository.findById(idInitiative);
        if(initiative.isPresent()){
            Initiative intiativeToModify = initiative.get();

            if(intiativeToModify.getIdOrganizers().contains(idUser)){
                intiativeToModify.getIdOrganizers().remove((Integer) idUser);
                initiativeRepository.save(intiativeToModify);
                System.out.println("Sto rimuovendo l'utente: " + idUser + " come organizzatore all'iniziativa: " + initiative.get().getName());
                return true;
            }
        }
        return false;

    }

    @GetMapping("/initiative/{idUtente}/getMyInitiatives")
    public List<Initiative> getMyInitiatives(@PathVariable(value = "idUtente") int idUtente){
        List<Initiative> initiatives = initiativeRepository.findAll();
        List<Initiative> createInitives = new java.util.ArrayList<>(initiatives.stream().filter(initiative -> initiative.getIdCreator() == idUtente).toList());
        List<Initiative> organizerInitives = initiatives.stream().filter(initiative -> initiative.getIdOrganizers().contains(idUtente)).toList();
        createInitives.addAll(organizerInitives);
        return createInitives;
    }

    @GetMapping("/initiative/{idUtente}/getMySubscribedInitiatives")
    public List<Initiative> getMySubscribedInitiatives(@PathVariable(value = "idUtente") int idUtente){
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
