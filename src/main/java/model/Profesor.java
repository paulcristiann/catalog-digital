package model;

import java.io.Serializable;

public class Profesor implements Serializable {
    private String nume;
    private String prenume;
    private String email;
    private String token;
    private String parola;

    private int id;
    private Actiuni actiune;


    private String eroare = "";

    public Profesor(String nume, String prenume, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;

    }

    public Profesor(String nume, String prenume, String email, String parola) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;

    }

    public Profesor(String nume, String prenume, String email, int id) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actiuni getActiune() {
        return actiune;
    }

    public void setActiune(Actiuni actiune) {
        this.actiune = actiune;
    }

    public String getEroare() {
        return eroare;
    }

    public void setEroare(String eroare) {
        this.eroare = eroare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public enum Actiuni {update, delete, save}
}
