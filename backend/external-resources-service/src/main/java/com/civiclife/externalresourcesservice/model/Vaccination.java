package com.civiclife.externalresourcesservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "vaccinations")
public class Vaccination {

    @Id
    private String id;
    private String email_owner;
    private Long date;
    private String location;
    private String description;
    private String vaccineName;
    private String dose;
    private String manufacturer;
    private String doctor;
    private String nurse;

    public Vaccination() {
    }

    public Vaccination(String id, String email_owner, Long date, String location, String description, String manufacturer, String doctor, String nurse) {
        this.id = id;
        this.email_owner = email_owner;
        this.date = date;
        this.location = location;
        this.description = description;
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

    public String getEmail_owner() {
        return email_owner;
    }

    public void setEmail_owner(String email_owner) {
        this.email_owner = email_owner;
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

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @Override
    public String toString(){
        return "Vaccination= {" +
                "id=" + id +
                "owner = " + email_owner + '\'' +
                ", vaccineName = " + vaccineName + '\'' +
                ", dose = " + dose + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", doctor='" + doctor + '\'' +
                ", nurse='" + nurse + '\'' +
                '}';
    }
}
