package com.civiclife.externalresourcesservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bonus")
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="id_owner")
    private String id_owner;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "end_date")
    private long end_date;

    public Bonus() {
    }

    public Bonus(String id_owner, String name, String description, String type, long end_date) {
        this.id_owner = id_owner;
        this.name = name;
        this.description = description;
        this.type = type;
        this.end_date = end_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_owner() {
        return id_owner;
    }

    public void setId_owner(String id_owner) {
        this.id_owner = id_owner;
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
                "id_owner=" + id_owner +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", endDate=" + end_date +
                '}';
    }
}
