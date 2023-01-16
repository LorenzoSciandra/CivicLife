package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.repo.PartyRepository;
import com.civiclife.voteservice.utils.ErrorMessage;
import com.civiclife.voteservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/partyAPI/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
public class PartyController {

    @Autowired
    private PartyRepository partyRepository;


    @GetMapping(value = "/parties", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @GetMapping("/parties/{partyId}")
    public Party getPartyById(@PathVariable String partyId) {
        Optional<Party> party = partyRepository.findById(partyId);
        return party.orElse(null);
    }

    @PostMapping("/party/create")
    public boolean createParty(@RequestBody Party party) {
        System.out.println("Sto aggiungendo\n" + party);
        partyRepository.save(party);
        return true;
    }

    @PostMapping("/party/update/{partyId}")
    public boolean updateParty(@PathVariable String partyId, @RequestBody Party party) {
        Optional<Party> partyToUpdate = partyRepository.findById(partyId);

        if (partyToUpdate.isPresent()) {
            partyToUpdate.get().setName(party.getName());
            partyToUpdate.get().setDescription(party.getDescription());
            partyToUpdate.get().setInfo(party.getInfo());
            partyRepository.save(partyToUpdate.get());
            return true;
        }
        return false;
    }

    @GetMapping("/party/delete/{partyId}")
    public boolean deleteParty(@PathVariable String partyId) {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isPresent()) {
            partyRepository.delete(party.get());
            return true;
        }
        return false;
    }

    @GetMapping("/party/addCandidate/{partyId}/{candidateId}")
    public boolean addCandidateToParty(@PathVariable String partyId, @PathVariable String candidateId) {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isPresent()) {

            System.out.println("Sto aggiungendo il candidato " + candidateId + " alla lista " + partyId);
            if (!party.get().getCandidateIdList().contains(candidateId)) {
                party.get().getCandidateIdList().add(candidateId);
                partyRepository.save(party.get());
                return true;
            }

        }
        return false;
    }

    @GetMapping("/party/removeCandidate/{partyId}/{candidateId}")
    public boolean removeCandidateFromParty(@PathVariable String partyId, @PathVariable String candidateId) {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isPresent()) {

            if (party.get().getCandidateIdList().contains(candidateId)) {
                party.get().getCandidateIdList().remove(candidateId);
                partyRepository.save(party.get());
                return true;
            }
        }

        return false;
    }

    @GetMapping("/party/allCandidates/{partyId}")
    public Set<String> getAllCandidatesFromParty(@PathVariable String partyId) {
        Optional<Party> party = partyRepository.findById(partyId);
        return party.map(Party::getCandidateIdList).orElse(null);
    }

    @GetMapping("/party/leader/{partyId}")
    public String getLeaderFromParty(@PathVariable String partyId) {
        Optional<Party> party = partyRepository.findById(partyId);
        return party.map(Party::getLeaderId).orElse(null);
    }

    @GetMapping("/party/setLeader/{partyId}/{candidateId}")
    public boolean setLeaderIdFromParty(@PathVariable String partyId, @PathVariable String candidateId) {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isPresent()) {

            party.get().getCandidateIdList().add(candidateId);
            party.get().setLeaderId(candidateId);
            partyRepository.save(party.get());
            return true;

        }
        return false;
    }

}
