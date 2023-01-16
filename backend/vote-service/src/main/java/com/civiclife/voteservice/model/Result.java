package com.civiclife.voteservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Data
@Document(collection = "results")
public class Result {
    @Id
    private String id;
    private String votationId;

    private String partyId;
    private int votes;
    private HashMap<String, Integer> numberOfVotesPerCandidate;

    public Result() {
    }

    public Result(String id, String votationId, String partyId, int votes, HashMap<String, Integer> numberOfVotesPerCandidate) {
        this.id = id;
        this.votationId = votationId;
        this.partyId = partyId;
        this.votes = votes;
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

    public HashMap<String, Integer> getNumberOfVotesPerCandidate() {
        return numberOfVotesPerCandidate;
    }

    public void setNumberOfVotesPerCandidate(HashMap<String, Integer> numberOfVotesPerCandidate) {
        this.numberOfVotesPerCandidate = numberOfVotesPerCandidate;
    }

    @Override
    public String toString() {
        return "Result{" +
                ", votationId='" + votationId + '\'' +
                ", partyId='" + partyId + '\'' +
                ", votes=" + votes +
                ", numberOfVotesPerCandidate=" + numberOfVotesPerCandidate +
                '}';
    }

}
