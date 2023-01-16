package com.civiclife.userservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.civiclife.userservice.model.StatusType;

import java.util.List;


@Data
@Document(collection = "users")
public class User {

    @Id
    private String email;
    private boolean admin;
    private String name;
    private String surname;
    private String fiscalCode;
    private String residence;
    private String domicile;
    private long birthDate;
    private StatusType status;
    private long telephonNumber;
    private boolean authorizeBonus;
    private boolean authorizeVaccine;

    public User(){

    }

    public User(String email, boolean admin, String name, String surname, String fiscalCode, String residence, String domicile, long birthDate, com.civiclife.userservice.model.StatusType status, long telephonNumber, boolean authorizeBonus, boolean authorizeVaccine) {
        this.email = email;
        this.admin = admin;
        this.name = name;
        this.surname = surname;
        this.fiscalCode = fiscalCode;
        this.residence = residence;
        this.domicile = domicile;
        this.birthDate = birthDate;
        this.status = status;
        this.telephonNumber = telephonNumber;
        this.authorizeBonus = authorizeBonus;
        this.authorizeVaccine = authorizeVaccine;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public long getTelephonNumber() {
        return telephonNumber;
    }

    public void setTelephonNumber(long telephonNumber) {
        this.telephonNumber = telephonNumber;
    }

    public boolean isAuthorizeBonus() {
        return authorizeBonus;
    }

    public void setAuthorizeBonus(boolean authorizeBonus) {
        this.authorizeBonus = authorizeBonus;
    }

    public boolean isAuthorizeVaccine() {
        return authorizeVaccine;
    }

    public void setAuthorizeVaccine(boolean authorizeVaccine) {
        this.authorizeVaccine = authorizeVaccine;
    }

    @Override
    public String toString(){
        return "User{" +
                "email='" + email + '\'' +
                ", admin=" + admin +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", fiscalCode='" + fiscalCode + '\'' +
                ", residence='" + residence + '\'' +
                ", domicile='" + domicile + '\'' +
                ", birthDate=" + birthDate +
                ", status=" + status +
                ", telephonNumber=" + telephonNumber +
                ", authorizeBonus=" + authorizeBonus +
                ", authorizeVaccine=" + authorizeVaccine +
                '}';
    }
}
