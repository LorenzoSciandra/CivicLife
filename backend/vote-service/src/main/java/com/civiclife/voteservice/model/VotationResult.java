package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VotationResult {

    private float numberOfVotes;

    private Set<String> votersIdList;

    private Set<PartyResult> partyResults;

    public float getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(float numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Set<String> getVotersIdList() {
        return votersIdList;
    }

    public void setVotersIdList(Set<String> votersIdList) {
        this.votersIdList = votersIdList;
    }

    public Set<PartyResult> getPartyResults() {
        return partyResults;
    }

    public void setPartyResults(Set<PartyResult> partyResults) {
        this.partyResults = partyResults;
    }
}
