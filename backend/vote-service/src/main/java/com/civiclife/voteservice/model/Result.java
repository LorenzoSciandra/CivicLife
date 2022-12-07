package com.civiclife.voteservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Result")
public class Result {

    @Id
    private long id;
    private long votationId;
    private long partyId;
    private int votes;
    private float percentage;
    private List<Candidate> candidateList;
    private List<Integer> numberOfVotesPerCandidate;

    public Result() {
    }

    public Result(long id, long votationId, long partyId, int votes, float percentage, List<Candidate> candidateList, List<Integer> numberOfVotesPerCandidate) {
        this.id = id;
        this.votationId = votationId;
        this.partyId = partyId;
        this.votes = votes;
        this.percentage = percentage;
        this.candidateList = candidateList;
        this.numberOfVotesPerCandidate = numberOfVotesPerCandidate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVotationId() {
        return votationId;
    }

    public void setVotationId(long votationId) {
        this.votationId = votationId;
    }

    public long getPartyId() {
        return partyId;
    }

    public void setPartyId(long partyId) {
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

    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<Candidate> candidateList) {
        this.candidateList = candidateList;
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
                ", candidateList=" + candidateList +
                ", numberOfVotesPerCandidate=" + numberOfVotesPerCandidate +
                '}';
    }

}
