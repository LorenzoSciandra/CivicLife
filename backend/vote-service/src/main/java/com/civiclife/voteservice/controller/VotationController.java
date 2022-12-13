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
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class VotationController {

    @Autowired
    private VotationRepository votationRepository;

    @Autowired
    private ResultController resultController;

    @Autowired
    private PartyController partyController;

    @Autowired
    private CandidateController candidateController;


    @GetMapping("/votations")
    public List<Votation> getAllVotations() {
        return votationRepository.findAll();
    }

    @GetMapping("/votations/{votationId}")
    public Votation getVotationById(@PathVariable String votationId) {
        Optional<Votation> votation = votationRepository.findVotationById(votationId);
        return votation.orElse(null);
    }

    @PostMapping("/votation/create")
    public boolean createVotation(@RequestBody Votation votation) {
        votationRepository.save(votation);
        return true;
    }

    @GetMapping("/votation/delete/{votationId}")
    public boolean deleteVotation(@PathVariable String votationId) {
        Optional<Votation> votation = votationRepository.findVotationById(votationId);
        if(votation.isPresent()) {
            votationRepository.delete(votation.get());
            return true;
        }
        return false;
    }

    @GetMapping("/votation/vote/{votationId}/{partyId}/{candidateId}/{voterId}")
    public boolean voteCandidate(@PathVariable String votationId, @PathVariable String partyId, @PathVariable String candidateId, @PathVariable String voterId) {
        Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
        Optional<Party> partyOptional = Optional.ofNullable(partyController.getPartyById(partyId));
        Optional<Candidate> candidateOptional = Optional.ofNullable(candidateController.getCandidateById(candidateId));
        Votation votation = votationOptional.orElse(null);
        if(votation!=null && partyOptional.isPresent() && candidateOptional.isPresent()) {
            if(!votation.getVotersIdList().contains(voterId) && checkDate(votation)) {
                String resultId = votation.getResultIdPerPartyId().get(partyId);
                Optional<Result> resultOptional = Optional.ofNullable(resultController.getResultById(resultId));
                if (resultOptional.isPresent()){
                    votation.getVotersIdList().add(voterId);
                    votation.setNumberOfVotes(votation.getNumberOfVotes()+1);
                    resultController.addVoteCandidate(resultId, candidateId);
                    votationRepository.save(votation);
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/votation/vote/{votationId}/{partyId}/{voterId}")
    public boolean voteParty(@PathVariable String votationId, @PathVariable String partyId, @PathVariable String voterId) {
        Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
        Optional<Party> partyOptional = Optional.ofNullable(partyController.getPartyById(partyId));
        if(votationOptional.isPresent() && partyOptional.isPresent()) {
            Votation votation = votationOptional.get();
            if(!votation.getVotersIdList().contains(voterId) && checkDate(votation)) {
                String resultId = votation.getResultIdPerPartyId().get(partyId);
                Optional<Result> resultOptional = Optional.ofNullable(resultController.getResultById(resultId));
                if (resultOptional.isPresent()){
                    Party party = partyOptional.get();
                    votation.getVotersIdList().add(voterId);
                    votation.setNumberOfVotes(votation.getNumberOfVotes()+1);
                    resultController.addVoteCandidate(resultId, party.getLeaderId());
                    votationRepository.save(votation);
                    return true;
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
        Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
        if(votationOptional.isPresent()) {
            Votation votation = votationOptional.get();
            votation.setStatus(Integer.parseInt(newStatus));
            votationRepository.save(votation);
            return true;
        }
        return false;
    }

    @GetMapping("/votation/finalize/{votationId}")
    public boolean finalizeVotation(@PathVariable String votationId) {
        Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
        if(votationOptional.isPresent()) {
            Votation votation = votationOptional.get();
            votation.setStatus(2);
            for (String partyId: votation.getResultIdPerPartyId().keySet()) {
                String resultId = votation.getResultIdPerPartyId().get(partyId);
                resultController.finalizeResult(resultId);
                votation.getPercentagePerPartyId().put(partyId, (float) (resultController.getResultById(resultId).getVotes() / votation.getNumberOfVotes()));
            }
            votationRepository.save(votation);
            return true;
        }
        return false;
    }

}
