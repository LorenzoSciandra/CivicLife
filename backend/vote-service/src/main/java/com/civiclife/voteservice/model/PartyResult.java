package com.civiclife.voteservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyResult {
    private String partyId;
    private int votes;
    private HashMap<String, Integer> numberOfVotesPerCandidate;

    public HashMap<String, Float> finalPartyResult;

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

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public HashMap<String, Float> getFinalPartyResult() {
        return finalPartyResult;
    }

    public void setFinalPartyResult(HashMap<String, Float> finalPartyResult) {
        this.finalPartyResult = finalPartyResult;
    }

    @Override
    public String toString() {
        return "{" +
                "partyId='" + partyId + '\'' + "," +
                "votes='" + votes + '\'' + "," +
                "numberOfVotesPerCandidate='" + numberOfVotesPerCandidate + '\'' +
                "finalPartyResult='" + finalPartyResult + '\'' +
                '}';
    }
}
