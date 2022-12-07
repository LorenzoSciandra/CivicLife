package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
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

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @GetMapping("/candidates/{candidateId}")
    public Candidate getCandidateById(@PathVariable String candidateId) {
        return candidateRepository.findByCandidateId(candidateId);
    }

    @PostMapping("/candidate/create")
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @PostMapping("/candidate/update/{candidateId}")
    public Candidate updateCandidate(@PathVariable String candidateId, @RequestBody Candidate candidate) {
        Candidate candidateToUpdate = candidateRepository.findByCandidateId(candidateId);
        candidateToUpdate.setName(candidate.getName());
        candidateToUpdate.setSurname(candidate.getSurname());
        candidateToUpdate.setDescription(candidate.getDescription());
        candidateToUpdate.setInfo(candidate.getInfo());
        candidateToUpdate.setPartyId(candidate.getPartyId());
        return candidateRepository.save(candidateToUpdate);
    }

    @GetMapping("/candidate/delete/{candidateId}")
    public void deleteCandidate(@PathVariable String candidateId) {
        candidateRepository.delete(candidateRepository.findByCandidateId(candidateId));
    }
}
