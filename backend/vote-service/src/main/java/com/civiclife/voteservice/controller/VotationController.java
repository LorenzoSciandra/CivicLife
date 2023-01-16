package com.civiclife.voteservice.controller;
import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.model.Votation;
import com.civiclife.voteservice.repo.CandidateRepository;
import com.civiclife.voteservice.repo.PartyRepository;
import com.civiclife.voteservice.repo.ResultRepository;
import com.civiclife.voteservice.repo.VotationRepository;
import com.civiclife.voteservice.utils.ErrorMessage;
import com.civiclife.voteservice.utils.ValidateCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.temporal.ChronoField;
import java.util.*;


@RestController
@RequestMapping("/votationAPI/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
@AllArgsConstructor
public class VotationController {

    @Autowired
    private VotationRepository votationRepository;
    private PartyRepository partyRepository;
    private CandidateRepository candidateRepository;
    private ResultRepository resultRepository;


    @GetMapping(value = "/votations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votation> getAllVotations() {
        return votationRepository.findAll();
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
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

    @DeleteMapping("/votation/delete/{votationId}")
    public boolean deleteVotation(@PathVariable String votationId) {
        Optional<Votation> votation = votationRepository.findVotationById(votationId);
        if(votation.isPresent()) {
            votationRepository.delete(votation.get());
            return true;
        }
        return false;
    }

    @GetMapping("/votation/vote/{votationId}/{partyId}/{candidateId}/{voterId}/{emailRichiedente}")
    public boolean voteCandidate(@PathVariable String votationId,
                                 @PathVariable String partyId,
                                 @PathVariable String candidateId,
                                 @PathVariable String voterId,
                                 @PathVariable String emailRichiedente) {

        if(emailRichiedente.equals(voterId)) {
            Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
            Optional<Party> partyOptional = partyRepository.findPartyById(partyId);
            Optional<Candidate> candidateOptional = candidateRepository.findCandidateById(candidateId);
            if (votationOptional.isPresent() && partyOptional.isPresent() && candidateOptional.isPresent()) {
                Votation votation = votationOptional.get();
                if (!votation.getVotersIdList().contains(voterId) && checkDate(votation)) {
                    String resultId = votation.getResultIdPerPartyId().get(partyId);
                    Optional<Result> resultOptional = resultRepository.findById(resultId);
                    if (resultOptional.isPresent()) {
                        Result result = resultOptional.get();
                        votation.getVotersIdList().add(voterId);
                        votation.setNumberOfVotes(votation.getNumberOfVotes() + 1);
                        result.setVotes(result.getVotes() + 1);
                        result.getNumberOfVotesPerCandidate().put(candidateId, result.getNumberOfVotesPerCandidate().get(candidateId) + 1);
                        resultRepository.save(result);
                        votationRepository.save(votation);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @GetMapping("/votation/vote/{votationId}/{partyId}/{voterId}/{emailRichiedente}")
    public boolean voteParty(@PathVariable String votationId,
                             @PathVariable String partyId,
                             @PathVariable String voterId,
                             @PathVariable String emailRichiedente) {

        if(emailRichiedente.equals(voterId)) {
            Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
            Optional<Party> partyOptional = partyRepository.findById(partyId);
            if (votationOptional.isPresent() && partyOptional.isPresent()) {
                Votation votation = votationOptional.get();
                if (!votation.getVotersIdList().contains(voterId) && checkDate(votation)) {
                    String resultId = votation.getResultIdPerPartyId().get(partyId);
                    Optional<Result> resultOptional = resultRepository.findById(resultId);
                    if (resultOptional.isPresent()) {
                        Result result = resultOptional.get();
                        Party party = partyOptional.get();
                        votation.getVotersIdList().add(voterId);
                        votation.setNumberOfVotes(votation.getNumberOfVotes() + 1);
                        result.setVotes(result.getVotes() + 1);
                        result.getNumberOfVotesPerCandidate().put(party.getLeaderId(), result.getNumberOfVotesPerCandidate().get(party.getLeaderId()) + 1);
                        votationRepository.save(votation);
                        resultRepository.save(result);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDate(Votation votation){
        return votation.getEndDate() > java.time.LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    }

    @GetMapping(value = "/votation/status/{votationId}/{newStatus}/{email}/{emailRichiedente}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean setStatus(@PathVariable String votationId,
                             @PathVariable Votation.VotationStatus newStatus,
                             @PathVariable String email,
                             @PathVariable String emailRichiedente) {

        if(email.equals(emailRichiedente)){
            Optional<Votation> votationOptional = votationRepository.findVotationById(votationId);
            if(votationOptional.isPresent()){
                Votation votation = votationOptional.get();

                String url = "http://localhost:8080/userAPI/v1/user/isAdmin    /" + email + "/" + emailRichiedente;

                boolean result = Boolean.TRUE.equals(new RestTemplate().getForObject(url, boolean.class));

                if(result){
                    votation.setStatus(newStatus);
                    if(newStatus.equals(Votation.VotationStatus.CLOSED)){
                        votation.setEndDate(java.time.LocalDateTime.now().get(ChronoField.MILLI_OF_DAY));
                        finalizeResults(votation);
                    }
                    votationRepository.save(votation);
                    return true;
                }
            }
        }
        return false;
    }

    public void finalizeResults(Votation votation){

        HashMap<String, Result> results = new HashMap<>();

        for(String resultParty : votation.getResultIdPerPartyId().values()){
            Optional<Result> resultOptional = resultRepository.findById(resultParty);
            if(resultOptional.isPresent()){
                Result result = resultOptional.get();
                results.put(resultParty, result);
            }
        }

        for (Result result : results.values()) {
            votation.getPercentagePerPartyId().put(result.getPartyId(), (float) (result.getVotes()/ votation.getNumberOfVotes()));
        }
    }

}
