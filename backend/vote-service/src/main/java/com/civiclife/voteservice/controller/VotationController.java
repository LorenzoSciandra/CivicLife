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
        Votation votation = votationOptional.orElse(null);
        if(votation!=null && partyController.getPartyById(partyId)!=null &&
                candidateController.getCandidateById(candidateId)!=null) {
            if(!votation.getvotersIdList().contains(voterId) && checkDate(votation)) {
                for (String resultId: votation.getresultIdList() ) {
                    Optional<Result> resultOptional = Optional.ofNullable(resultController.getResultById(resultId));
                    if (resultOptional.isPresent()){
                        Result result = resultOptional.get();
                        if (Objects.equals(resultId, partyId)){
                            int index = 0;
                            for(String currentCandidateId: result.getcandidateIdList()){
                                if(Objects.equals(currentCandidateId, candidateId)){
                                    result.getNumberOfVotesPerCandidate().set(index, result.getNumberOfVotesPerCandidate().get(index)+1);
                                    resultController.addVoteCandidate(result.getId(), currentCandidateId);
                                    votationRepository.save(votation);
                                    return true;
                                }
                                index++;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @GetMapping("/votation/vote/{votationId}/{partyId}/{voterId}")
    public boolean voteParty(@PathVariable String votationId, @PathVariable String partyId, @PathVariable String voterId) {
        Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
        if(votationOptional.isPresent() && partyController.getPartyById(partyId)!=null) {
            Votation votation = votationOptional.get();
            if(!votation.getvotersIdList().contains(voterId) && checkDate(votation)) {
                for (String resultId: votation.getresultIdList()) {
                    Optional<Result> resultOptional = Optional.ofNullable(resultController.getResultById(resultId));
                    if(resultOptional.isPresent()){
                        Result result = resultOptional.get();
                        if(Objects.equals(result.getPartyId(), partyId)){
                            Party party = partyController.getPartyById(partyId);
                            String leaderId = party.getLeaderId();
                            int index = 0;
                            for (String candidateId: result.getcandidateIdList()) {
                                if(Objects.equals(candidateId, leaderId)){
                                    result.getNumberOfVotesPerCandidate().set(index, result.getNumberOfVotesPerCandidate().get(index) + 1);
                                    resultController.addVoteCandidate(result.getId(), leaderId);
                                    votationRepository.save(votation);
                                    return true;
                                }
                            }
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
            votationRepository.save(votation);
            for (String resultId: votation.getresultIdList()) {
                resultController.finalizeResult(resultId, votation.getvotersIdList().size());
            }
            return true;
        }
        return false;
    }

}
