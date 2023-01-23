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
public class PartyResult {
    private String partyId;
    private float votes;
    private Float percentage;

    private Set<CandidateResult> candidateResults;

    public float getVotes() {
        return votes;
    }
    public void setVotes(float votes) {
        this.votes = votes;
    }
    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Set<CandidateResult> getCandidateResults() {
        return candidateResults;
    }

    public void setCandidateResults(Set<CandidateResult> candidateResults) {
        this.candidateResults = candidateResults;
    }
}
