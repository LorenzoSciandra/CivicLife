package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotationResult {

    private int numberOfVotes;

    private Set<String> votersIdList;

    // <PartyId : PartyResult>
    private Set<PartyResult> partyResults;

    private HashMap<String, Float> finalResults;

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Set<String> getVotersIdList() {
        return votersIdList;
    }

    public void setVotersIdList(Set<String> votersIdList) {
        this.votersIdList = votersIdList;
    }

    public HashMap<String, Float> getFinalResults() {
        return finalResults;
    }

    public void setFinalResults(HashMap<String, Float> finalResults) {
        this.finalResults = finalResults;
    }

    public Set<PartyResult> getPartyResults() {
        return partyResults;
    }

    public void setPartyResults(Set<PartyResult> partyResults) {
        this.partyResults = partyResults;
    }

    @Override
    public String toString() {
        return "VotationResult{" +
                ", numberOfVotes=" + numberOfVotes +
                ", votersIdList=" + votersIdList +
                ", partyResults=" + partyResults +
                ", finalResults=" + finalResults +
                '}';
    }
}
