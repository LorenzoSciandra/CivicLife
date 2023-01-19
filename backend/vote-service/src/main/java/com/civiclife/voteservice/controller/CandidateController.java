package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.repo.CandidateRepository;
import com.civiclife.voteservice.utils.ErrorMessage;
import com.civiclife.voteservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/candidateAPI/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;


    @GetMapping(value = "/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @GetMapping(value = "/candidate/get/{candidateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate getCandidateById(@PathVariable String candidateId) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        return candidateOptional.orElse(null);
    }

    @PostMapping("/postman/candidate/create")
    public boolean createCandidate(@RequestBody Candidate[] candidate) {
        candidateRepository.saveAll(List.of(candidate));
        return true;
    }


    @GetMapping("/postman/candidate/delete/{candidateId}")
    public boolean deleteCandidate(@PathVariable String candidateId) {
        Optional<Candidate> candidateOptional= candidateRepository.findById(candidateId);
        if(candidateOptional.isPresent()) {
            candidateRepository.delete(candidateOptional.get());
            return true;
        }
        return false;
    }
}
