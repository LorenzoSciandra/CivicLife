package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.repo.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping("/results")
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    @GetMapping("/results/{resultId}")
    public Result getResultById(@PathVariable String resultId) {
        Optional<Result> optionalResult = resultRepository.findResultById(resultId);
        return optionalResult.orElse(null);
    }

    @PostMapping("/result/create")
    public boolean createResult(@RequestBody Result result) {
        resultRepository.save(result);
        return true;
    }

    @PostMapping("/result/update/{resultId}")
    public boolean updateResult(@PathVariable String resultId, @RequestBody Result result) {
        Optional<Result> optionalResult = resultRepository.findResultById(resultId);
        if(optionalResult.isPresent()) {
            optionalResult.get().setVotationId(result.getVotationId());
            optionalResult.get().setPartyId(result.getPartyId());
            optionalResult.get().setVotes(result.getVotes());
            optionalResult.get().setPercentage(result.getPercentage());
            optionalResult.get().setcandidateIdList(result.getcandidateIdList());
            optionalResult.get().setNumberOfVotesPerCandidate(result.getNumberOfVotesPerCandidate());
            resultRepository.save(optionalResult.get());
            return true;
        }
        return false;
    }

    @GetMapping("/result/delete/{resultId}")
    public boolean deleteResult(@PathVariable String resultId) {
        Optional<Result> optionalResult = resultRepository.findResultById(resultId);
        if(optionalResult.isPresent()) {
            resultRepository.delete(optionalResult.get());
            return true;
        }
        return false;
    }
    public Result getResultsByPartyVotationId(String partyId, String votationId) {
        List<Result> results = resultRepository.findAll();
        for (Result result : results) {
            if (Objects.equals(result.getPartyId(), partyId) && Objects.equals(result.getVotationId(), votationId)) {
                return result;
            }
        }
        return null;
    }


    public boolean addVoteCandidate(String id, String candidateId) {
        Optional<Result> optionalResult = resultRepository.findById(id);
        Result result = optionalResult.orElse(null);
        if (result != null) {
            for(int i = 0; i < result.getcandidateIdList().size(); i++){
                if(Objects.equals(result.getcandidateIdList().get(i), candidateId)){
                    result.getNumberOfVotesPerCandidate().set(i, result.getNumberOfVotesPerCandidate().get(i) + 1);
                }
            }
            resultRepository.save(result);
            return true;
        }
        return false;
    }

    public boolean finalizeResult(String id, int totalVotesVotation) {
        Optional<Result> optionalResult = resultRepository.findById(String.valueOf(id));
        Result result = optionalResult.orElse(null);
        if (result != null) {
            int totalVotes = 0;
            for (int i = 0; i < result.getNumberOfVotesPerCandidate().size(); i++) {
                totalVotes += result.getNumberOfVotesPerCandidate().get(i);
            }
            result.setPercentage((float) totalVotes / totalVotesVotation);
            result.setVotes(totalVotes);
            resultRepository.save(result);
            return true;
        }
        else {
            return false;
        }
    }
}
