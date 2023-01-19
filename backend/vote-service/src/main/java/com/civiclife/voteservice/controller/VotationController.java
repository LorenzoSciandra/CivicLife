package com.civiclife.voteservice.controller;
import com.civiclife.voteservice.model.*;
import com.civiclife.voteservice.repo.CandidateRepository;
import com.civiclife.voteservice.repo.PartyRepository;
import com.civiclife.voteservice.repo.VotationRepository;
import com.civiclife.voteservice.service.ApiCall;
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
    @Autowired
    private ApiCall apiCall;


    @GetMapping(value = "/votations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votation> getAllVotations() {
        return votationRepository.findAll();
    }

    @GetMapping(value = "/votations/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votation> getAllVotationsByStatus(@PathVariable Votation.VotationStatus status) {
        return votationRepository.votationsByStatus(status);
    }

    @GetMapping(value = "/votations/done/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votation> getAllVotationsDoneByUser(@PathVariable String userId) {
        List<Votation> votations = votationRepository.findAll();
        return votations.stream().filter(votation -> votation.getVotationResult().getVotersIdList().contains(userId)).toList();
    }

    @GetMapping(value = "/votation/get/parties/{votationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Party> getAllPartiesFromVotation(@PathVariable String votationId){
        Optional<Votation> optionalVotation = votationRepository.findById(votationId);
        if(optionalVotation.isPresent()){
            Votation votation = optionalVotation.get();
            return votation.getParties();
        }
        return new HashSet<>();
    }

    @GetMapping(value = "votation/get/result/{votationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VotationResult getResult(@PathVariable String votationId) {
        Optional<Votation> optionalVotation = votationRepository.findById(votationId);
        return optionalVotation.map(Votation::getVotationResult).orElse(null);
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @PostMapping("/postman/votation/create")
    public boolean createVotation(@RequestBody Votation[] votations) {
        votationRepository.saveAll(Set.of(votations));
        return true;
    }

    @DeleteMapping("/postman/votation/delete/{votationId}")
    public boolean deleteVotation(@PathVariable String votationId) {
        Optional<Votation> votation = votationRepository.findById(votationId);
        if(votation.isPresent()) {
            votationRepository.delete(votation.get());
            return true;
        }
        return false;
    }

    @GetMapping("/postman/result/delete/{votationId}")
    public boolean deleteResult(@PathVariable String votationId) {

        Optional<Votation> optionalVotation = votationRepository.findById(votationId);

        if(optionalVotation.isPresent()) {
            Votation votation = optionalVotation.get();
            votation.setVotationResult(new VotationResult());
            votationRepository.save(votation);
            return true;
        }
        return false;
    }

    @GetMapping(value = "/votation/vote/{votationId}/{partyId}/{candidateId}/{voterId}/{emailRichiedente}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean voteCandidate(@PathVariable String votationId,
                                 @PathVariable String partyId,
                                 @PathVariable String candidateId,
                                 @PathVariable String voterId,
                                 @PathVariable String emailRichiedente) {

        if(emailRichiedente.equals(voterId)) {
            Optional<Votation> votationOptional = votationRepository.findById(votationId);

            if (votationOptional.isPresent()) {
                Votation votation = votationOptional.get();
                if (!votation.getVotationResult().getVotersIdList().contains(voterId)
                        && votation.getStatus().equals(Votation.VotationStatus.OPEN)
                        && checkDate(votation)) {
                    VotationResult votationResult = votation.getVotationResult();
                    for (PartyResult partyResult : votationResult.getPartyResults()) {
                        if (partyResult.getPartyId().equals(partyId)) {
                            Set<String> candidateIdList = partyResult.getNumberOfVotesPerCandidate().keySet();
                            if (candidateIdList.contains(candidateId)) {
                                HashMap<String, Integer> numberOfVotesPerCandidate = partyResult.getNumberOfVotesPerCandidate();
                                numberOfVotesPerCandidate.put(candidateId, numberOfVotesPerCandidate.get(candidateId) + 1);
                                partyResult.setNumberOfVotesPerCandidate(numberOfVotesPerCandidate);
                                partyResult.setVotes(partyResult.getVotes() + 1);
                                Set<String> votersIdList = votationResult.getVotersIdList();
                                votersIdList.add(voterId);
                                votationResult.setVotersIdList(votersIdList);
                                votationResult.setNumberOfVotes(votationResult.getNumberOfVotes() + 1);
                                votation.setVotationResult(votationResult);
                                votationRepository.save(votation);
                                return true;

                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    @GetMapping(value = "/votation/vote/{votationId}/{partyId}/{voterId}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean voteParty(@PathVariable String votationId,
                             @PathVariable String partyId,
                             @PathVariable String voterId,
                             @PathVariable String emailRichiedente) {

        if(emailRichiedente.equals(voterId)) {
            Optional<Votation> votationOptional = votationRepository.findById(votationId);
            if (votationOptional.isPresent()) {
                Votation votation = votationOptional.get();
                if (!votation.getVotationResult().getVotersIdList().contains(voterId)
                        && votation.getStatus().equals(Votation.VotationStatus.OPEN)
                        && checkDate(votation)) {
                    VotationResult votationResult = votation.getVotationResult();
                    for (PartyResult partyResult : votationResult.getPartyResults()) {
                        if (partyResult.getPartyId().equals(partyId)) {
                            Set<String> candidateIdList = partyResult.getNumberOfVotesPerCandidate().keySet();
                            Optional<Party> partyOptional = partyRepository.findById(partyId);
                            if(partyOptional.isPresent()){
                                String candidateId = partyOptional.get().getLeaderId();
                                if (candidateIdList.contains(candidateId)) {
                                    HashMap<String, Integer> numberOfVotesPerCandidate = partyResult.getNumberOfVotesPerCandidate();
                                    numberOfVotesPerCandidate.put(candidateId, numberOfVotesPerCandidate.get(candidateId) + 1);
                                    partyResult.setNumberOfVotesPerCandidate(numberOfVotesPerCandidate);
                                    partyResult.setVotes(partyResult.getVotes() + 1);
                                    Set<String> votersIdList = votationResult.getVotersIdList();
                                    votersIdList.add(voterId);
                                    votationResult.setVotersIdList(votersIdList);
                                    votationResult.setNumberOfVotes(votationResult.getNumberOfVotes() + 1);
                                    votation.setVotationResult(votationResult);
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

    @PostMapping(value = "/votation/status/{votationId}/{email}/{emailRichiedente}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean setStatus(@PathVariable String votationId,
                             @PathVariable String email,
                             @PathVariable String emailRichiedente,
                             @RequestBody String status) {

        Votation.VotationStatus newStatus = parseVotationStatus(status);
        if(newStatus!=null && email.equals(emailRichiedente)){
            Optional<Votation> votationOptional = votationRepository.findById(votationId);
            if(votationOptional.isPresent()){
                Votation votation = votationOptional.get();

                boolean isAuthorized = apiCall.isAdmin(email);

                if(isAuthorized){
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

    private Votation.VotationStatus parseVotationStatus(String status){
        return switch (status) {
            case "OPEN" -> Votation.VotationStatus.OPEN;
            case "CLOSED" -> Votation.VotationStatus.CLOSED;
            case "TERMINATED" -> Votation.VotationStatus.TERMINATED;
            default -> null;
        };
    }

    public void finalizeResults(Votation votation){
        VotationResult votationResult = votation.getVotationResult();
        HashMap<String, Float> numberOfVotesPerParty = new HashMap<>();

        for(PartyResult partyResult : votationResult.getPartyResults()){
            HashMap<String, Float> percentagePerCandidate = new HashMap<>();
            Set<String> candidateIdList = partyResult.getNumberOfVotesPerCandidate().keySet();

            for(String candidate: candidateIdList){
                float percentage = (float) partyResult.getNumberOfVotesPerCandidate().get(candidate) / votationResult.getNumberOfVotes();
                percentagePerCandidate.put(candidate, percentage);
            }
            partyResult.setFinalPartyResult(percentagePerCandidate);
            numberOfVotesPerParty.put(partyResult.getPartyId(), (float) partyResult.getVotes()/votationResult.getNumberOfVotes());
        }
        votationResult.setFinalResults(numberOfVotesPerParty);
    }

}
