package com.civiclife.initiativeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitiativeReadOnly {
    private String name;
    private String description;
    private String location;
    private Initiative.InitiativeType type;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Initiative.InitiativeType getType() {
        return type;
    }

    public void setType(Initiative.InitiativeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InitiativeReadOnly{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", type=" + type +
                '}';
    }
}
