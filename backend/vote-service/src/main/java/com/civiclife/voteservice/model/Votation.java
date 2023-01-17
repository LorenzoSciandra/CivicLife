package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashMap;
import java.util.Set;

@Data
@Document(collection = "votations")
@AllArgsConstructor
@NoArgsConstructor
public class Votation {

    public enum VotationStatus {
        OPEN, CLOSED, TERMINATED
    }

    @Id
    private String title;
    private String description;
    private long startDate;
    private long endDate;
    private VotationStatus status;
    private int numberOfVotes;
    private Set<String> votersIdList;

    // <PartyId : ResultId>
    private HashMap<String, String> resultIdPerPartyId;

    private HashMap<String, Float> percentagePerPartyId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setResultIdPerPartyId(HashMap<String, String> resultIdPerPartyId) {
        this.resultIdPerPartyId = resultIdPerPartyId;
    }

    public HashMap<String, String> getResultIdPerPartyId() {
        return resultIdPerPartyId;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public VotationStatus getStatus() {
        return status;
    }

    public void setStatus(VotationStatus status) {
        this.status = status;
    }

    public HashMap<String, Float> getPercentagePerPartyId() {
        return percentagePerPartyId;
    }

    public void setPercentagePerPartyId(HashMap<String, Float> percentagePerPartyId) {
        this.percentagePerPartyId = percentagePerPartyId;
    }

    public Set<String> getVotersIdList() {
        return votersIdList;
    }

    public void setVotersIdList(Set<String> votersIdList) {
        this.votersIdList = votersIdList;
    }

    @Override
    public String toString() {
        return "Votation{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", votersIdList=" + votersIdList +
                ", resultIdPerPartyId=" + resultIdPerPartyId +
                ", numberOfVotes=" + numberOfVotes +
                ", percentagePerPartyId=" + percentagePerPartyId +
                '}';
    }
}
