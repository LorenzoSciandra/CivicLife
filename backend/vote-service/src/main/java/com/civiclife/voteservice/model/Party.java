package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document(collection = "parties")
public class Party {

    @Id
    private String id;
    private String name;

    private String logoLink;
    private String description;
    private String info;
    private Set<String> candidateIdList;
    private String leaderId;

    public Party() {
    }

    public Party(String id, String name, String logoLink, String description, String info, Set<String> candidateIdList, String leaderId) {
        this.id = id;
        this.name = name;
        this.logoLink = logoLink;
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

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leader) {
        this.leaderId = leader;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public Set<String> getCandidateIdList() {
        return candidateIdList;
    }

    public void setCandidateIdList(Set<String> candidateIdList) {
        this.candidateIdList = candidateIdList;
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
