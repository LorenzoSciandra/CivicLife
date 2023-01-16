package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.repo.ResultRepository;
import com.civiclife.voteservice.utils.ErrorMessage;
import com.civiclife.voteservice.utils.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/resultAPI/v1")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 600)
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping(value = "/results", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    @GetMapping(value = "/error/{code}/{path}/{method}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage error(@PathVariable(value = "code") ValidateCode code,
                              @PathVariable(value = "path") String path,
                              @PathVariable(value = "method") String method) {
        String pathUrl = new String(Base64.getDecoder().decode(path));
        return new ErrorMessage(code, pathUrl, method);
    }

    @GetMapping("/results/{resultId}")
    public Result getResultById(@PathVariable String resultId) {
        Optional<Result> optionalResult = resultRepository.findById(resultId);
        return optionalResult.orElse(null);
    }

    @PostMapping("/result/create")
    public boolean createResult(@RequestBody Result result) {
        resultRepository.save(result);
        return true;
    }

    @PostMapping("/result/update/{resultId}")
    public boolean updateResult(@PathVariable String resultId, @RequestBody Result result) {
        Optional<Result> optionalResult = resultRepository.findById(resultId);
        if(optionalResult.isPresent()) {
            optionalResult.get().setVotationId(result.getVotationId());
            optionalResult.get().setPartyId(result.getPartyId());
            optionalResult.get().setVotes(result.getVotes());
            optionalResult.get().setNumberOfVotesPerCandidate(result.getNumberOfVotesPerCandidate());
            resultRepository.save(optionalResult.get());
            return true;
        }
        return false;
    }

    @GetMapping("/result/delete/{resultId}")
    public boolean deleteResult(@PathVariable String resultId) {
        Optional<Result> optionalResult = resultRepository.findById(resultId);
        if(optionalResult.isPresent()) {
            resultRepository.delete(optionalResult.get());
            return true;
        }
        return false;
    }

    @GetMapping(value = "/result/{votationId}/{partyId}")
    public Result getResultsByPartyVotationId(@PathVariable(value = "votationId") String votationId,
                                              @PathVariable(value = "partyId") String partyId) {
        Optional<Result> optionalResult = resultRepository.findResultByVotationAndPaAndPartyId(votationId, partyId);
        if(optionalResult.isPresent()){
            return optionalResult.get();
        }

        return null;
    }

    public boolean addVoteCandidate(String id, String candidateId) {
        Optional<Result> optionalResult = resultRepository.findById(id);
        Result result = optionalResult.orElse(null);
        if (result != null) {
            result.setVotes(result.getVotes() + 1);
            result.getNumberOfVotesPerCandidate().put(candidateId, result.getNumberOfVotesPerCandidate().get(candidateId) + 1);
            resultRepository.save(result);
            return true;
        }
        return false;
    }
}
