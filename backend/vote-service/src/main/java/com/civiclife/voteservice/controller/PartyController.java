package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.model.Votation;
import com.civiclife.voteservice.repo.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class PartyController {

    @Autowired
    private PartyRepository partyRepository;


    @GetMapping("/parties")
    public List<Party> getAllParties() {
        return partyRepository.findAll();
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
    public List<String> getAllCandidatesFromParty(@PathVariable String partyId) {
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

            if (!party.get().getCandidateIdList().contains(candidateId)) {
                party.get().getCandidateIdList().add(candidateId);
            }
            party.get().setLeaderId(candidateId);
            partyRepository.save(party.get());
            return true;

        }
        return false;
    }

}
