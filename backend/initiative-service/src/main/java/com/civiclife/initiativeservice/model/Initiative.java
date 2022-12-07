package com.civiclife.initiativeservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

@Document(collection = "initiatives")
public class Initiative {

    @Id
    private String id;

    private String name;
    private String description;
    private String status;
    private String type;
    private int idCreator;
    private List<Integer> idOrganizers;
    private List<Integer> idMembers;
    private long startDate;
    private long endDate;
    private String location;

    public Initiative() {
    }

    public Initiative(String name, String description, String status, String type, int idCreator, List<Integer> idOrganizers, List<Integer> idMembers, long startDate, long endDate, String location) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.idCreator = idCreator;
        this.idOrganizers = idOrganizers;
        this.idMembers = idMembers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(int idCreator) {
        this.idCreator = idCreator;
    }

    public List<Integer> getIdOrganizers() {
        return idOrganizers;
    }

    public void setIdOrganizers(List<Integer> idOrganizers) {
        this.idOrganizers = idOrganizers;
    }

    public List<Integer> getIdMembers() {
        return idMembers;
    }

    public void setIdMembers(List<Integer> idMembers) {
        this.idMembers = idMembers;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Initiative{" +
                "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + ", status='" + status + '\'' + ", type='" + type + '\'' + ", idCreator=" + idCreator + ", idOrganizers=" + idOrganizers + ", idMembers=" + idMembers + ", startDate=" + startDate + ", endDate=" + endDate + ", location='" + location + '\'' + '}';
    }

}
