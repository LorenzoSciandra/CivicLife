package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private List<String> partyIdList;
    private int status;
    private List<String> votersIdList;
    private List<String> resultIdList;

    public Votation() {
    }

    public Votation(String title, String description, long startDate, long endDate, List<String> partyIdList, int status, List<String> votersIdList, List<String> resultIdList) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.partyIdList = partyIdList;
        this.status = status;
        this.votersIdList = votersIdList;
        this.resultIdList = resultIdList;
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

    public List<String> getpartyIdList() {
        return partyIdList;
    }

    public void setpartyIdList(List<String> partyIdList) {
        this.partyIdList = partyIdList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getvotersIdList() {
        return votersIdList;
    }

    public void setvotersIdList(List<String> votersIdList) {
        this.votersIdList = votersIdList;
    }

    public List<String> getresultIdList() {
        return resultIdList;
    }

    public void setresultIdList(List<String> resultIdList) {
        this.resultIdList = resultIdList;
    }

    @Override
    public String toString() {
        return "Votation{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", partyIdList=" + partyIdList +
                ", status=" + status +
                ", votersIdList=" + votersIdList +
                ", resultIdList=" + resultIdList +
                '}';
    }
}
