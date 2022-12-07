package com.civiclife.voteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Party")
public class Party {

    @Id
    private long id;
    private String name;
    private String description;
    private String info;
    private List<Candidate> candidateList;
    private Candidate leader;

    public Party() {
    }

    public Party(long id, String name, String description, String info, List<Candidate> candidateList, Candidate leader) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.info = info;
        this.candidateList = candidateList;
        this.leader = leader;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<Candidate> candidateList) {
        this.candidateList = candidateList;
    }

    public Candidate getLeader() {
        return leader;
    }

    public void setLeader(Candidate leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return "Party{" +
                "id=" + id + ", " +
                "name='" + name + "', " +
                "description='" + description + "', " +
                "info='" + info + "', " +
                "candidateList=" + candidateList +
                ", leader=" + leader +
                "}";
    }
}
