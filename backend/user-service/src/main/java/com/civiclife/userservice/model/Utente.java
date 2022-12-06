package com.civiclife.userservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "utente")
public class Utente {

    // create ArrayList of int
    @ElementCollection
    private List<Integer> idIniziativeCreate = new ArrayList<Integer>();
    @ElementCollection
    private List<Integer> idIniziativeSottoscritte = new ArrayList<Integer>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "amministratore")
    private boolean amministratore;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "email")
    private String email;

    @Column(name = "codicefiscale")
    private String codicefiscale;

    @Column(name="residenza")
    private String residenza;

    @Column(name="domicilio")
    private String domicilio;

    @Column(name="data_nascita")
    private String data_nascita;

    @Column(name="stato")
    private int stato;

    @Column(name="telefono")
    private long telefono;

    public Utente() {
        this.amministratore = false;
    }

    public Utente(String nome, Boolean amministratore, String cognome, String email, String codicefiscale, String residenza, String domicilio, String data_nascita, int stato, long telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.amministratore = amministratore;
        this.email = email;
        this.codicefiscale = codicefiscale;
        this.residenza = residenza;
        this.domicilio = domicilio;
        this.data_nascita = data_nascita;
        this.stato = stato;
        this.telefono = telefono;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Boolean getAmministratore() {
        return amministratore;
    }

    public String getEmail() {
        return email;
    }

    public String getCodicefiscale() {
        return codicefiscale;
    }

    public String getResidenza() {
        return residenza;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getData_nascita() {
        return data_nascita;
    }

    public int getStato() {
        return stato;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCodicefiscale(String codicefiscale) {
        this.codicefiscale = codicefiscale;
    }

    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setData_nascita(String data_nascita) {
        this.data_nascita = data_nascita;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public void setAmministratore(boolean amministratore) {
        this.amministratore = amministratore;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public List<Integer> getIdIniziativeCreate() {
        return idIniziativeCreate;
    }

    public void setIdIniziativeCreate(List<Integer> idIniziativeCreate) {
        this.idIniziativeCreate = idIniziativeCreate;
    }

    public List<Integer> getIdIniziativeSottoscritte() {
        return idIniziativeSottoscritte;
    }

public void setIdIniziativeSottoscritte(List<Integer> idIniziativeSottoscritte) {
        this.idIniziativeSottoscritte = idIniziativeSottoscritte;
    }


   @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", amministratore=" + amministratore +
                ", email='" + email + '\'' +
                ", codicefiscale='" + codicefiscale + '\'' +
                ", residenza='" + residenza + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", data_nascita='" + data_nascita + '\'' +
                ", stato=" + stato +
                ", telefono=" + telefono +
                '}';
    }
}
