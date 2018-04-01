package model;

import java.io.Serializable;

public class Clasa implements Serializable {
    private int id;
    private String nume;
    private int id_diriginte;
    public enum Actiuni {create,read,update,delete}
    private String eroare="";
    private Actiuni actiune;
    private String solicitant; //pentru cand vreau sa iau din BD doar clasele la care preda un profesor

    public Clasa(String nume) {
        this.nume = nume;
    }

    public Clasa(int id, String nume, int id_diriginte, Actiuni actiune) {
        this.id = id;
        this.nume = nume;
        this.id_diriginte = id_diriginte;
        this.actiune = actiune;
    }

    public Clasa() {
    }

    @Override
    public String toString() {
        return nume;
    }

    public String getEroare() {
        return eroare;
    }

    public void setEroare(String eroare) {
        this.eroare = eroare;
    }

    public String getSolicitant() {
        return solicitant;
    }

    public void setSolicitant(String solicitant) {
        this.solicitant = solicitant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getId_diriginte() {
        return id_diriginte;
    }

    public void setId_diriginte(int id_diriginte) {
        this.id_diriginte = id_diriginte;
    }

    public Actiuni getActiune() {
        return actiune;
    }

    public void setActiune(Actiuni actiune) {
        this.actiune = actiune;
    }
}
