package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "parties")
public class Party {

    @Id
    private String id;
    private String name;
    private String description;
    private String info;
    private List<String> candidateIdList;
    private String leaderId;

    public Party() {
    }

    public Party(String name, String description, String info, List<String> candidateIdList, String leaderId) {
        this.name = name;
        this.description = description;
        this.info = info;
        this.candidateIdList = candidateIdList;
        this.leaderId = leaderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getCandidateIdList() {
        return candidateIdList;
    }

    public void setcandidateIdList(List<String> candidateIdList) {
        this.candidateIdList = candidateIdList;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leader) {
        this.leaderId = leader;
    }

    @Override
    public String toString() {
        return "Party{" +
                "id=" + id + ", " +
                "name='" + name + "', " +
                "description='" + description + "', " +
                "info='" + info + "', " +
                "candidateIdList=" + candidateIdList +
                ", leader=" + leaderId +
                "}";
    }
}
