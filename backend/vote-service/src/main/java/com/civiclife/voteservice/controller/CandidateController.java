package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.repo.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PartyController partyController;

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @GetMapping("/candidates/{candidateId}")
    public Candidate getCandidateById(@PathVariable String candidateId) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        return candidateOptional.orElse(null);
    }

    @PostMapping("/candidate/create")
    public boolean createCandidate(@RequestBody Candidate candidate) {
        candidateRepository.save(candidate);
        return true;
    }

    @PostMapping("/candidate/update/{candidateId}")
    public boolean updateCandidate(@PathVariable String candidateId, @RequestBody Candidate candidate) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);

        if(candidateOptional.isPresent()) {
            candidateOptional.get().setName(candidate.getName());
            candidateOptional.get().setDescription(candidate.getDescription());
            candidateOptional.get().setInfo(candidate.getInfo());
            candidateOptional.get().setPartyId(candidate.getPartyId());
            candidateRepository.save(candidateOptional.get());
            return true;
        }
        else{
            return false;
        }
    }

    @GetMapping("/candidate/setParty/{candidateId}/{partyId}")
    public boolean updateCandidateParty(@PathVariable String candidateId, @PathVariable String partyId){
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        if(candidateOptional.isPresent()){
            if(partyController.getPartyById(partyId) != null){
                candidateOptional.get().setPartyId(partyId);
                candidateRepository.save(candidateOptional.get());
                return true;
            }

        }
        return false;
    }


    @GetMapping("/candidate/delete/{candidateId}")
    public boolean deleteCandidate(@PathVariable String candidateId) {
        Optional<Candidate> candidateOptional= candidateRepository.findCandidateById(candidateId);
        if(candidateOptional.isPresent()) {
            candidateRepository.delete(candidateOptional.get());
            return true;
        }
        return false;
    }
}
