package com.civiclife.externalresourcesservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "vaccinations")
public class Vaccination {

    @Id
    private String id;
    private String id_owner;
    private String type;
    private Long date;
    private String location;
    private String description;
    private String name;
    private String vaccine;
    private String manufacturer;
    private String doctor;
    private String nurse;

    public Vaccination() {
    }

    public Vaccination(String id_owner, String type, Long date, String location, String description, String name, String vaccine, String manufacturer, String doctor, String nurse) {
        this.id_owner = id_owner;
        this.type = type;
        this.date = date;
        this.location = location;
        this.description = description;
        this.name = name;
        this.vaccine = vaccine;
        this.manufacturer = manufacturer;
        this.doctor = doctor;
        this.nurse = nurse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_owner() {
        return id_owner;
    }

    public void setId_owner(String id_owner) {
        this.id_owner = id_owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }

    @Override
    public String toString(){
        return "Vaccination= {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", vaccine='" + vaccine + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", doctor='" + doctor + '\'' +
                ", nurse='" + nurse + '\'' +
                '}';
    }
}
