package com.civiclife.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private boolean admin;
    private String name;
    private String surname;
    private String fiscalCode;
    private String residence;
    private String domicile;
    private long birthDate;
    private int status;
    private long telephonNumber;

    private List <String> votationsId;
    private List <String> initiativesCreatedId;
    private List <String> initiativeSubscribedId;
    private List <String> bonusId;
    private List <String> vaccinationId;

    public User(){

    }

    public User(boolean admin, String name, String surname, String email, String fiscalCode, String residence, String domicile, long birthDate, int status, long telephonNumber, List<String> votationsId, List<String> initiativesCreatedId, List<String> initiativeSubscribedId, List<String> bonusId, List<String> vaccinationId) {
        this.admin = admin;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.fiscalCode = fiscalCode;
        this.residence = residence;
        this.domicile = domicile;
        this.birthDate = birthDate;
        this.status = status;
        this.telephonNumber = telephonNumber;
        this.votationsId = votationsId;
        this.initiativesCreatedId = initiativesCreatedId;
        this.initiativeSubscribedId = initiativeSubscribedId;
        this.bonusId = bonusId;
        this.vaccinationId = vaccinationId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTelephonNumber() {
        return telephonNumber;
    }

    public void setTelephonNumber(long telephonNumber) {
        this.telephonNumber = telephonNumber;
    }


    public List<String> getVotationsId() {
        return votationsId;
    }

    public void setVotationsId(List<String> votationsId) {
        this.votationsId = votationsId;
    }

    public List<String> getInitiativesCreatedId() {
        return initiativesCreatedId;
    }

    public void setInitiativesCreatedId(List<String> initiativesCreatedId) {
        this.initiativesCreatedId = initiativesCreatedId;
    }

    public List<String> getInitiativeSubscribedId() {
        return initiativeSubscribedId;
    }

    public void setInitiativeSubscribedId(List<String> initiativeSubscribedId) {
        this.initiativeSubscribedId = initiativeSubscribedId;
    }

    public List<String> getBonusId() {
        return bonusId;
    }

    public void setBonusId(List<String> bonusId) {
        this.bonusId = bonusId;
    }

    public List<String> getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(List<String> vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", admin=" + admin +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", fiscalCode='" + fiscalCode + '\'' +
                ", residence='" + residence + '\'' +
                ", domicile='" + domicile + '\'' +
                ", birthDate=" + birthDate +
                ", status=" + status +
                ", telephonNumber=" + telephonNumber +
                '}';
    }
}
