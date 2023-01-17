package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Party;
import com.civiclife.voteservice.repo.PartyRepository;
import com.civiclife.voteservice.utils.ErrorMessage;
import com.civiclife.voteservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/partyAPI/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
public class PartyController {

    @Autowired
    private PartyRepository partyRepository;


    @GetMapping(value = "/parties", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @GetMapping(value = "/party/get/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Party getPartyById(@PathVariable String partyId) {
        Optional<Party> partyOptional = partyRepository.findById(partyId);
        return partyOptional.orElse(null);
    }


    @GetMapping("/party/get/allCandidates/{partyId}")
    public Set<String> getAllCandidatesFromParty(@PathVariable String partyId) {
        Optional<Party> party = partyRepository.findById(partyId);
        return party.map(Party::getCandidateIdList).orElse(null);
    }

    @PostMapping("postman/party/create")
    public boolean createParty(@RequestBody Party[] party) {
        partyRepository.saveAll(Set.of(party));
        return true;
    }

    @GetMapping("postman/party/delete/{partyId}")
    public boolean deleteParty(@PathVariable String partyId) {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isPresent()) {
            partyRepository.delete(party.get());
            return true;
        }
        return false;
    }


    @GetMapping("postman/party/setLeader/{partyId}/{candidateId}")
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
