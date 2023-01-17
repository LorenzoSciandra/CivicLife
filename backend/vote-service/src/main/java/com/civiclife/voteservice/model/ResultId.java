package com.civiclife.voteservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultId {

    private String votationId;
    private String partyId;

    public ResultId(String votationId, String partyId) {
        this.votationId = votationId;
        this.partyId = partyId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getVotationId() {
        return votationId;
    }

    public void setVotationId(String votationId) {
        this.votationId = votationId;
    }

    @Override
    public String toString() {
        return "{" +
                "partyId='" + partyId + '\'' + "," +
                "votationId='" + votationId + '\'' +
                '}';
    }
}
