package com.civiclife.initiativeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "initiatives")
public class Initiative {

    public enum InitiativeStatus {
        PROGRAMMED, IN_PROGRESS, FINISHED
    }

    public enum InitiativeType {
        SOCIAL, CULTURAL, SPORT, EDUCATIONAL, ENVIRONMENTAL, OTHER, HEALTH
    }

    @Id
    private String id;

    private String name;
    private String description;
    private InitiativeStatus status;
    private InitiativeType type;
    private String idCreator;
    private Set<String> idOrganizers;
    private Set<String> idMembers;
    private long startDate;
    private long endDate;
    private String location;

    public Initiative() {
    }

    public Initiative(String id, String name, String description, InitiativeStatus status, InitiativeType type, String idCreator, Set<String> idOrganizers, Set<String> idMembers, long startDate, long endDate, String location) {
        this.id = id;
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

    public InitiativeStatus getStatus() {
        return status;
    }

    public void setStatus(InitiativeStatus status) {
        this.status = status;
    }

    public InitiativeType getType() {
        return type;
    }

    public void setType(InitiativeType type) {
        this.type = type;
    }

    public String getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(String idCreator) {
        this.idCreator = idCreator;
    }

    public Set<String> getIdOrganizers() {
        return idOrganizers;
    }

    public void setIdOrganizers(Set<String> idOrganizers) {
        this.idOrganizers = idOrganizers;
    }

    public Set<String> getIdMembers() {
        return idMembers;
    }

    public void setIdMembers(Set<String> idMembers) {
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
