package com.civiclife.voteservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private float percentage;
    private List<String> candidateIdList;
    private List<Integer> numberOfVotesPerCandidate;

    public Result() {
    }

    public Result(String votationId, String partyId, int votes, float percentage, List<String> candidateIdList, List<Integer> numberOfVotesPerCandidate) {
        this.votationId = votationId;
        this.partyId = partyId;
        this.votes = votes;
        this.percentage = percentage;
        this.candidateIdList = candidateIdList;
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

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public List<String> getcandidateIdList() {
        return candidateIdList;
    }

    public void setcandidateIdList(List<String> candidateIdList) {
        this.candidateIdList = candidateIdList;
    }

    public List<Integer> getNumberOfVotesPerCandidate() {
        return numberOfVotesPerCandidate;
    }

    public void setNumberOfVotesPerCandidate(List<Integer> numberOfVotesPerCandidate) {
        this.numberOfVotesPerCandidate = numberOfVotesPerCandidate;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", votationId='" + votationId + '\'' +
                ", partyId='" + partyId + '\'' +
                ", votes=" + votes +
                ", percentage=" + percentage +
                ", candidateIdList=" + candidateIdList +
                ", numberOfVotesPerCandidate=" + numberOfVotesPerCandidate +
                '}';
    }

}
