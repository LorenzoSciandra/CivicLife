package com.civiclife.voteservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "results")
public class Result {

    @Id
    private String id;
    private String votationId;
    private String partyId;
    private int votes;

    private HashMap<String,Float> percentageByCandidateId;
    private HashMap<String, Integer> numberOfVotesPerCandidate;

    public Result() {
    }

    public Result(String votationId, String partyId, int votes, HashMap<String,Float> percentageByCandidateId,  HashMap<String, Integer> numberOfVotesPerCandidate) {
        this.votationId = votationId;
        this.partyId = partyId;
        this.votes = votes;
        this.percentageByCandidateId = percentageByCandidateId;
        this.numberOfVotesPerCandidate = numberOfVotesPerCandidate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVotationId() {
        return votationId;
    }

    public void setVotationId(String votationId) {
        this.votationId = votationId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public HashMap<String, Float> getPercentageByCandidateId() {
        return percentageByCandidateId;
    }

    public void setPercentageByCandidateId(HashMap<String, Float> percentageByCandidateId) {
        this.percentageByCandidateId = percentageByCandidateId;
    }

    public HashMap<String, Integer> getNumberOfVotesPerCandidate() {
        return numberOfVotesPerCandidate;
    }

    public void setNumberOfVotesPerCandidate(HashMap<String, Integer> numberOfVotesPerCandidate) {
        this.numberOfVotesPerCandidate = numberOfVotesPerCandidate;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", votationId='" + votationId + '\'' +
                ", partyId='" + partyId + '\'' +
                ", votes=" + votes +
                ", percentage=" + percentageByCandidateId +
                ", numberOfVotesPerCandidate=" + numberOfVotesPerCandidate +
                '}';
    }

}
