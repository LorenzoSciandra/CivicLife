package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "votations")
public class Votation {

    @Id
    private String id;
    private String title;
    private String description;
    private long startDate;
    private long endDate;
    private int status;

    private int numberOfVotes;
    private List<String> votersIdList;
    private HashMap<String, String> resultIdPerPartyId;

    private HashMap<String, Float> percentagePerPartyId;

    public Votation() {
    }

    public Votation(String title, String description, long startDate, long endDate, int status, int numberOfVotes, List<String> votersIdList, HashMap<String, String> resultIdPerPartyId, HashMap<String, Float> percentagePerPartyId) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.votersIdList = votersIdList;
        this.resultIdPerPartyId = resultIdPerPartyId;
        this.numberOfVotes = numberOfVotes;
        this.percentagePerPartyId = percentagePerPartyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setVotersIdList(List<String> votersIdList) {
        this.votersIdList = votersIdList;
    }

    public void setResultIdPerPartyId(HashMap<String, String> resultIdPerPartyId) {
        this.resultIdPerPartyId = resultIdPerPartyId;
    }

    public HashMap<String, Float> getPercentagePerPartyId() {
        return percentagePerPartyId;
    }

    public void setPercentagePerPartyId(HashMap<String, Float> percentagePerPartyId) {
        this.percentagePerPartyId = percentagePerPartyId;
    }

    public List<String> getVotersIdList() {
        return votersIdList;
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

    @Override
    public String toString() {
        return "Votation{" +
                "id='" + id + '\'' +
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
