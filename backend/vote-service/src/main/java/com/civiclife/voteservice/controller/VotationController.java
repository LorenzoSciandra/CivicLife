package com.civiclife.voteservice.controller;
import com.civiclife.voteservice.model.*;
import com.civiclife.voteservice.repo.PartyRepository;
import com.civiclife.voteservice.repo.VotationRepository;
import com.civiclife.voteservice.service.ApiCall;
import com.civiclife.voteservice.utils.ErrorMessage;
import com.civiclife.voteservice.utils.ValidateCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/votationAPI/v1")
@AllArgsConstructor
public class VotationController {

    @Autowired
    private VotationRepository votationRepository;
    private PartyRepository partyRepository;
    @Autowired
    private ApiCall apiCall;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/votations/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Votation> getAllVotationsByStatus() {
        return votationRepository.votationsByStatus(Votation.VotationStatus.valueOf("ACTIVE"));
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/votations/terminated", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Votation> getAllVotationsByStatusTerminated() {
        return votationRepository.votationsByStatus(Votation.VotationStatus.valueOf("TERMINATED"));
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value= "/votations/programmed/{userId}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Votation> getAllVotationsByStatusProgrammed(@PathVariable String userId,
                                                            @PathVariable String emailRichiedente){

        if(userId.equals(emailRichiedente)) {
            boolean isAdmin = apiCall.isAdmin(emailRichiedente);
            if (isAdmin) {
                return votationRepository.votationsByStatus(Votation.VotationStatus.valueOf("PROGRAMMED"));
            }
        }
        return new HashSet<>();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/votations/done/{userId}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Votation> getAllVotationsDoneByUser(@PathVariable String userId,
                                                   @PathVariable String emailRichiedente) {

        if(userId.equals(emailRichiedente)){
            return votationRepository.votationsDoneByUser(userId);
        }

        return new HashSet<>();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/votation/get/parties/{votationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Party> getAllPartiesFromVotation(@PathVariable String votationId){
        String votationIdDecoded = new String(Base64.getDecoder().decode(votationId));
        Optional<Votation> optionalVotation = votationRepository.findById(votationIdDecoded);
        if(optionalVotation.isPresent()){
            Votation votation = optionalVotation.get();
            return partyRepository.findAllById(votation.getPartiesIds());
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/votation/voteCandidate/{votationId}/{partyId}/{candidateId}/{voterId}/{emailRichiedente}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean voteCandidate(@PathVariable String votationId,
                                 @PathVariable String partyId,
                                 @PathVariable String candidateId,
                                 @PathVariable String voterId,
                                 @PathVariable String emailRichiedente) {

        if(emailRichiedente.equals(voterId)) {
            String decodedVotationId = new String(Base64.getDecoder().decode(votationId));
            Optional<Votation> votationOptional = votationRepository.findById(decodedVotationId);
            if (votationOptional.isPresent()) {
                Votation votation = votationOptional.get();
                boolean isAdmin = apiCall.isAdmin(emailRichiedente);
                if (!isAdmin && !votation.getVotationResult().getVotersIdList().contains(voterId)
                        && votation.getStatus().equals(Votation.VotationStatus.ACTIVE)
                        && checkDate(votation)) {
                    VotationResult votationResult = votation.getVotationResult();
                    String decodedPartyId = new String(Base64.getDecoder().decode(partyId));
                    for (PartyResult partyResult : votationResult.getPartyResults()) {
                        if (partyResult.getPartyId().equals(decodedPartyId)) {
                            for(CandidateResult candidateResult: partyResult.getCandidateResults()){
                                if(candidateResult.getCandidateId().equals(candidateId)){
                                    candidateResult.setVotes(candidateResult.getVotes() + 1);
                                    partyResult.setVotes(partyResult.getVotes() + 1);
                                    votationResult.setNumberOfVotes(votationResult.getNumberOfVotes() +1);
                                    Set<String> voters = votationResult.getVotersIdList();
                                    voters.add(voterId);
                                    votationResult.setVotersIdList(voters);
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

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @GetMapping(value = "/votation/voteParty/{votationId}/{partyId}/{voterId}/{emailRichiedente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean voteParty(@PathVariable String votationId,
                             @PathVariable String partyId,
                             @PathVariable String voterId,
                             @PathVariable String emailRichiedente) {

        if(emailRichiedente.equals(voterId)) {
            String decodedVotationId = new String(Base64.getDecoder().decode(votationId));
            Optional<Votation> votationOptional = votationRepository.findById(decodedVotationId);
            if (votationOptional.isPresent()) {
                boolean isAdmin = apiCall.isAdmin(emailRichiedente);
                Votation votation = votationOptional.get();
                if (!isAdmin && !votation.getVotationResult().getVotersIdList().contains(voterId)
                        && votation.getStatus().equals(Votation.VotationStatus.ACTIVE)
                        && checkDate(votation)) {
                    VotationResult votationResult = votation.getVotationResult();
                    String decodedPartyId = new String(Base64.getDecoder().decode(partyId));
                    for (PartyResult partyResult : votationResult.getPartyResults()) {
                        if (partyResult.getPartyId().equals(decodedPartyId)) {
                            for(CandidateResult candidateResult: partyResult.getCandidateResults()){
                                if(candidateResult.isLeader()){
                                    candidateResult.setVotes(candidateResult.getVotes() + 1);
                                    partyResult.setVotes(partyResult.getVotes() + 1);
                                    votationResult.setNumberOfVotes(votationResult.getNumberOfVotes() + 1);
                                    Set<String> voters = votationResult.getVotersIdList();
                                    voters.add(voterId);
                                    votationResult.setVotersIdList(voters);
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
        long now = System.currentTimeMillis()/1000;
        return now < votation.getEndDate() && now > votation.getStartDate();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
    @PostMapping(value = "/votation/updateStatus/{votationId}/{email}/{emailRichiedente}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean setStatus(@PathVariable String votationId,
                             @PathVariable String email,
                             @PathVariable String emailRichiedente,
                             @RequestBody String status) {

        Votation.VotationStatus newStatus = parseVotationStatus(status);
        if(newStatus!=null && email.equals(emailRichiedente)){
            String decodedVotationId = new String(Base64.getDecoder().decode(votationId));
            Optional<Votation> votationOptional = votationRepository.findById(decodedVotationId);
            if(votationOptional.isPresent()){
                Votation votation = votationOptional.get();
                if(checkUpdateStatus(votation.getStatus(), newStatus)) {
                    boolean isAuthorized = apiCall.isAdmin(email);
                    if (isAuthorized) {
                        votation.setStatus(newStatus);
                        if (newStatus.equals(Votation.VotationStatus.TERMINATED)) {
                            votation.setEndDate(System.currentTimeMillis() / 1000);
                            finalizeResults(votation);
                        }
                        votationRepository.save(votation);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Votation.VotationStatus parseVotationStatus(String status){
        return switch (status) {
            case "ACTIVE" -> Votation.VotationStatus.ACTIVE;
            case "TERMINATED" -> Votation.VotationStatus.TERMINATED;
            case "PROGRAMMED" -> Votation.VotationStatus.PROGRAMMED;
            default -> null;
        };
    }

    private void finalizeResults(Votation votation) {
        VotationResult votationResult = votation.getVotationResult();

        if(votationResult.getNumberOfVotes() != 0) {
            for (PartyResult partyResult : votationResult.getPartyResults()) {
                partyResult.setPercentage(partyResult.getVotes() / votationResult.getNumberOfVotes());
                for (CandidateResult candidateResult : partyResult.getCandidateResults()) {
                    candidateResult.setPercentage(candidateResult.getVotes() / votationResult.getNumberOfVotes());
                }
            }
        }
    }

    private boolean checkUpdateStatus(Votation.VotationStatus oldStatus, Votation.VotationStatus newStatus){
        return switch (oldStatus) {
            case ACTIVE -> newStatus.equals(Votation.VotationStatus.TERMINATED);
            case TERMINATED -> false;
            case PROGRAMMED -> newStatus.equals(Votation.VotationStatus.ACTIVE);
        };
    }

    // POSTMAN TESTS

    @GetMapping(value = "postman/votations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votation> getAllVotations() {
        return  votationRepository.findAll();
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

    @GetMapping("/postman/votationResult/delete/{votationId}")
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
}
