package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "parties")
public class Party {

    @Id
    private String name;
    private String logoLink;
    private String description;
    private Set<String> candidateIdList;
    private String leaderId;

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
                "name='" + name + "', " +
                "description='" + description + "', " +
                "candidateIdList=" + candidateIdList +
                ", leader=" + leaderId +
                "}";
    }
}
