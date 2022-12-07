package com.civiclife.voteservice.controller;
import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.model.Votation;
import com.civiclife.voteservice.repo.VotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class VotationController {

    @Autowired
    private VotationRepository votationRepository;

    private ResultController resultController;

    private PartyController partyController;

    @GetMapping("/votations")
    public List<Votation> getAllVotations() {
        return votationRepository.findAll();
    }

    @GetMapping("/votations/{votationId}")
    public Votation getVotationById(@PathVariable String votationId) {
        return votationRepository.findByVotationId(votationId);
    }

    @PostMapping("/votation/create")
    public Votation createVotation(@RequestBody Votation votation) {
        return votationRepository.save(votation);
    }

    @GetMapping("/votation/delete/{votationId}")
    public boolean deleteVotation(@PathVariable String votationId) {
        votationRepository.delete(votationRepository.findByVotationId(votationId));
        return true;
    }

    @GetMapping("/votation/vote/{votationId}/{partyId}/{candidateId}/{voterId}")
    public boolean voteCandidate(@PathVariable String votationId, @PathVariable String partyId, @PathVariable String candidateId, @PathVariable String voterId) {
        Votation votation = votationRepository.findByVotationId(votationId);

        if(!votation.getVotersList().contains(Long.parseLong(voterId)) && checkDate(votation)) {
            for (Result result: votation.getResultList() ) {
                if (result.getPartyId() == Long.parseLong(partyId)){
                   int index = 0;
                    for(Candidate candidate: result.getCandidateList()){
                        if(candidate.getId() == Long.parseLong(candidateId)){
                            result.getNumberOfVotesPerCandidate().set(index, result.getNumberOfVotesPerCandidate().get(index)+1);
                            resultController.addVoteCandidate(result.getId(), candidate.getId());
                            votationRepository.save(votation);
                            return true;
                        }
                        index++;
                    }
                }
            }
        }
        return false;
    }

    @GetMapping("/votation/vote/{votationId}/{partyId}/{voterId}")
    public boolean voteParty(@PathVariable String votationId, @PathVariable String partyId, @PathVariable String voterId) {
        Votation votation = votationRepository.findByVotationId(votationId);
        long voter = Long.parseLong(voterId);

        if(!votation.getVotersList().contains(voter) && checkDate(votation)) {
            for (Result result: votation.getResultList()) {
                if(result.getPartyId()==Long.parseLong(partyId)){
                    Party party = partyController.getPartyById(partyId);
                    long leaderId = party.getLeader().getId();
                    int index = 0;
                    for (Candidate candidate: result.getCandidateList()) {
                        if(candidate.getId() == leaderId){
                            result.getNumberOfVotesPerCandidate().set(index, result.getNumberOfVotesPerCandidate().get(index) + 1);
                            resultController.addVoteCandidate(result.getId(), leaderId);
                            votationRepository.save(votation);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean checkDate(Votation votation){
        return votation.getEndDate() > java.time.LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    }

    //TODO: controllo sia admin
    @GetMapping("/votation/status/{votationId}/{newStatus}")
    public boolean setStatus(@PathVariable String votationId, @PathVariable String newStatus) {
        Votation votation = votationRepository.findByVotationId(votationId);
        votation.setStatus(Integer.parseInt(newStatus));
        votationRepository.save(votation);
        return true;
    }

    @GetMapping("/votation/finalize/{votationId}")
    public boolean finalizeVotation(@PathVariable String votationId) {
        Votation votation = votationRepository.findByVotationId(votationId);
        votation.setStatus(2);
        for (Result result: votation.getResultList()) {
            resultController.finalizeResult(result.getId(), votation.getVotersList().size());
        }
        votationRepository.save(votation);
        return true;
    }

}
