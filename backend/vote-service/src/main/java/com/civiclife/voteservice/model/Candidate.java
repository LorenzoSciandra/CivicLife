package com.civiclife.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "candidates")
public class Candidate {

    @Id
    private String id;

    private String imageLink;
    private String partyId;
    private String name;
    private String surname;
    private String description;
    private String info;

    public Candidate(String id, String imageLink, String partyId, String name, String surname, String description, String info) {
        this.id = id;
        this.imageLink = imageLink;
        this.partyId = partyId;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.info = info;
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

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
                ", imageLink='" + imageLink + '\'' +
                '}';
    }

}
