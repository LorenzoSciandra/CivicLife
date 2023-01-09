package com.civiclife.externalresourcesservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "bonuses")
public class Bonus {

    @Id
    private String id;
    private String email_owner;
    private String name;
    private String description;
    private String type;
    private long end_date;

    public Bonus() {
    }

    public Bonus(String id, String email_owner, String name, String description, String type, long end_date) {
        this.id = id;
        this.email_owner = email_owner;
        this.name = name;
        this.description = description;
        this.type = type;
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail_owner() {
        return email_owner;
    }

    public void setEmail_owner(String email_owner) {
        this.email_owner = email_owner;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "Bonus{" +
                "id=" + id +
                "emai_owner=" + email_owner +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", endDate=" + end_date +
                '}';
    }
}
