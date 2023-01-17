package com.civiclife.voteservice.controller;

import com.civiclife.voteservice.model.Result;
import com.civiclife.voteservice.model.ResultId;
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

    @GetMapping(value = "/results/{votationId}/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getResultById(@PathVariable String votationId,
                                @PathVariable String partyId) {

        ResultId resultId = new ResultId(votationId, partyId);
        Optional<Result> optionalResult = resultRepository.findById(resultId);
        return optionalResult.orElse(null);
    }

    @PostMapping("postman/result/create")
    public boolean createResult(@RequestBody Result[] results) {
        resultRepository.saveAll(List.of(results));
        return true;
    }

    @GetMapping("postman/result/delete/{partyId}/{votationId}")
    public boolean deleteResult(@PathVariable String partyId,
                                @PathVariable String votationId) {
        ResultId resultId = new ResultId(votationId, partyId);
        Optional<Result> optionalResult = resultRepository.findById(resultId);
        if(optionalResult.isPresent()) {
            resultRepository.delete(optionalResult.get());
            return true;
        }
        return false;
    }
}
