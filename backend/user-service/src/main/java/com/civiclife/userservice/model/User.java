package com.civiclife.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private UserStatus status;
    private long telephonNumber;
    private boolean authorizeBonus;
    private boolean authorizeVaccine;

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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
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
}
