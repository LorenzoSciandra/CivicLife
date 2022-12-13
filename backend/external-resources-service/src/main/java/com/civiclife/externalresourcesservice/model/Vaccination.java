package com.civiclife.externalresourcesservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vaccination")
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name= "date")
    private Long date;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "vaccine")
    private String vaccine;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "doctor")
    private String doctor;

    @Column(name="nurse")
    private String nurse;

    public Vaccination(String type, Long date, String location, String description, String name, String vaccine, String manufacturer, String doctor, String nurse) {
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

    public Vaccination() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
