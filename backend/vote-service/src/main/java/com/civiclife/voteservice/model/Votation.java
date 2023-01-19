package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private Set<Party> parties;
    private VotationResult votationResult;

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

    public VotationStatus getStatus() {
        return status;
    }

    public void setStatus(VotationStatus status) {
        this.status = status;
    }

    public VotationResult getVotationResult() {
        return votationResult;
    }

    public void setVotationResult(VotationResult votationResult) {
        this.votationResult = votationResult;
    }

    public Set<Party> getParties() {
        return parties;
    }

    public void setParties(Set<Party> parties) {
        this.parties = parties;
    }

    @Override
    public String toString() {
        return "Votation{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}
