package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.model.Party;
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

    private ResultController resultController;

    private CandidateController candidateController;

    @GetMapping("/parties")
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @GetMapping("/parties/{partyId}")
    public Party getPartyById(@PathVariable String partyId) {
        return partyRepository.findByPartyId(partyId);
    }

    @PostMapping("/party/create")
    public Party createParty(@RequestBody Party party) {
        return partyRepository.save(party);
    }

    @PostMapping("/party/update/{partyId}")
    public Party updateParty(@PathVariable String partyId, @RequestBody Party party) {
        Party partyToUpdate = partyRepository.findByPartyId(partyId);
        partyToUpdate.setName(party.getName());
        partyToUpdate.setDescription(party.getDescription());
        partyToUpdate.setInfo(party.getInfo());
        partyToUpdate.setCandidateList(party.getCandidateList());
        return partyRepository.save(partyToUpdate);
    }

    @GetMapping("/party/delete/{partyId}")
    public void deleteParty(@PathVariable String partyId) {
        partyRepository.delete(partyRepository.findByPartyId(partyId));
    }

    @GetMapping("/party/addCandidate/{partyId}/{candidateId}")
    public void addCandidateToParty(@PathVariable String partyId, @PathVariable String candidateId) {
        Party party = partyRepository.findByPartyId(partyId);
        party.getCandidateList().add(candidateController.getCandidateById(candidateId));
        partyRepository.save(party);
    }

    @GetMapping("/party/removeCandidate/{partyId}/{candidateId}")
    public void removeCandidateFromParty(@PathVariable String partyId, @PathVariable String candidateId) {
        Party party = partyRepository.findByPartyId(partyId);
        party.getCandidateList().remove(candidateController.getCandidateById(candidateId));
        partyRepository.save(party);
    }

    @GetMapping("/party/allCandidates/{partyId}")
    public List<Candidate> getAllCandidatesFromParty(@PathVariable String partyId) {
        return partyRepository.findByPartyId(partyId).getCandidateList();
    }

    @GetMapping("/party/leader/{partyId}")
    public Candidate getLeaderFromParty(@PathVariable String partyId) {
        return partyRepository.findByPartyId(partyId).getLeader();
    }

    @GetMapping("/party/setLeader/{partyId}/{candidateId}")
    public void setLeaderFromParty(@PathVariable String partyId, @PathVariable String candidateId) {
        Party party = partyRepository.findByPartyId(partyId);
        party.setLeader(candidateController.getCandidateById(candidateId));
        partyRepository.save(party);
    }

    @GetMapping("/party/results/{partyId}/{electionId}")
    public Result getResultForPartyFromVotation(@PathVariable String partyId, @PathVariable String electionId) {
        return resultController.getResultsByPartyVotationId(Long.parseLong(partyId), Long.parseLong(electionId));
    }

}
