package com.civiclife.voteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Votation")
public class Votation {

    @Id
    private long id;
    private String title;
    private String description;
    private long startDate;
    private long endDate;
    private List<Party> partyList;
    private int status;
    private List<Long> votersList;
    private List<Result> resultList;

    public Votation() {
    }

    public Votation(long id, String title, String description, long startDate, long endDate, List<Party> partyList, int status, List<Long> votersList, List<Result> resultList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.partyList = partyList;
        this.status = status;
        this.votersList = votersList;
        this.resultList = resultList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Party> getPartyList() {
        return partyList;
    }

    public void setPartyList(List<Party> partyList) {
        this.partyList = partyList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Long> getVotersList() {
        return votersList;
    }

    public void setVotersList(List<Long> votersList) {
        this.votersList = votersList;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return "Votation{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", partyList=" + partyList +
                ", status=" + status +
                ", votersList=" + votersList +
                ", resultList=" + resultList +
                '}';
    }
}
