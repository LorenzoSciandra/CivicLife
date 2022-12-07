package com.civiclife.voteservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Candidate")
public class Candidate {

    @Id
    private long id;

    private long partyId;
    private String name;
    private String surname;
    private String description;
    private String info;

    public Candidate() {
    }

    public Candidate(long id, String name, String surname, String description, String info, long partyId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.info = info;
        this.partyId = partyId;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public long getPartyId() {
        return partyId;
    }

    public void setPartyId(long partyId) {
        this.partyId = partyId;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", description='" + description + '\'' +
                ", info='" + info + '\'' +
                ", partyId=" + partyId +
                '}';
    }

}
