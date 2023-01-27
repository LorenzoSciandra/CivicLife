package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.repo.CandidateRepository;
import com.civiclife.voteservice.repo.PartyRepository;
import com.civiclife.voteservice.utils.ErrorMessage;
import com.civiclife.voteservice.utils.ValidateCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/partyAPI/v1")
@AllArgsConstructor
public class PartyController {

    @Autowired
    private PartyRepository partyRepository;

    private CandidateRepository candidateRepository;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 1000)
    @GetMapping(value = "/parties", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 1000)
    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 1000)
    @GetMapping(value = "/party/get/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Party getPartyById(@PathVariable String partyId) {
        String decodedPartyId = new String(Base64.getDecoder().decode(partyId));
        Optional<Party> partyOptional = partyRepository.findById(decodedPartyId);
        return partyOptional.orElse(null);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 1000)
    @GetMapping(value = "/party/get/allCandidates/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Candidate> getAllCandidatesFromParty(@PathVariable String partyId) {
        String decodedPartyId = new String(Base64.getDecoder().decode(partyId));
        Optional<Party> optionalParty = partyRepository.findById(decodedPartyId);
        if(optionalParty.isPresent()) {
            Party party = optionalParty.get();
            return candidateRepository.findAllById(party.getCandidateIdList());
        }
        return new ArrayList<>();
    }

    // POSTMAN TESTS

    @PostMapping("/postman/party/create")
    public boolean createParty(@RequestBody Party[] party) {
        partyRepository.saveAll(Set.of(party));
        return true;
    }

    @GetMapping("/postman/party/delete/{partyId}")
    public boolean deleteParty(@PathVariable String partyId) {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isPresent()) {
            partyRepository.delete(party.get());
            return true;
        }
        return false;
    }

    @GetMapping("/postman/party/setLeader/{partyId}/{candidateId}")
    public boolean setLeaderIdFromParty(@PathVariable String partyId, @PathVariable String candidateId) {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isPresent()) {

            party.get().getCandidateIdList().add(candidateId);
            party.get().setLeaderId(candidateId);
            partyRepository.save(party.get());
            return true;

        }
        return false;
    }

}
