package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Candidate;
import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.repo.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return resultRepository.findByResultId(resultId);
    }

    @PostMapping("/result/create")
    public Result createResult(@RequestBody Result result) {
        return resultRepository.save(result);
    }

    @PostMapping("/result/update/{resultId}")
    public Result updateResult(@PathVariable String resultId, @RequestBody Result result) {
        Result resultToUpdate = resultRepository.findByResultId(resultId);
        resultToUpdate.setVotationId(result.getVotationId());
        resultToUpdate.setPartyId(result.getPartyId());
        resultToUpdate.setVotes(result.getVotes());
        resultToUpdate.setPercentage(result.getPercentage());
        resultToUpdate.setCandidateList(result.getCandidateList());
        resultToUpdate.setNumberOfVotesPerCandidate(result.getNumberOfVotesPerCandidate());
        return resultRepository.save(resultToUpdate);
    }

    @GetMapping("/result/delete/{resultId}")
    public void deleteResult(@PathVariable String resultId) {
        resultRepository.delete(resultRepository.findByResultId(resultId));
    }
    public Result getResultsByPartyVotationId(long partyId, long votationId) {
        List<Result> results = resultRepository.findAll();
        for (Result result : results) {
            if (result.getPartyId() == partyId && result.getVotationId() == votationId) {
                return result;
            }
        }
        return null;
    }


    public void addVoteCandidate(long id, long candidateId) {
        Result result = resultRepository.findByResultId(String.valueOf(id));

        for(int i = 0; i < result.getCandidateList().size(); i++){
            if(result.getCandidateList().get(i).getId() == candidateId){
                result.getNumberOfVotesPerCandidate().set(i, result.getNumberOfVotesPerCandidate().get(i) + 1);
            }
        }

        resultRepository.save(result);
    }

    public void finalizeResult(long id, int totalVotesVotation) {
        Result result = resultRepository.findByResultId(String.valueOf(id));
        int totalVotes = 0;
        for (int i = 0; i < result.getNumberOfVotesPerCandidate().size(); i++) {
            totalVotes += result.getNumberOfVotesPerCandidate().get(i);
        }
        result.setPercentage((float) totalVotes / totalVotesVotation);
        result.setVotes(totalVotes);
        resultRepository.save(result);
    }
}
