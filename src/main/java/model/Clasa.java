package model;

import java.io.Serializable;

public class Clasa  extends Logged implements Serializable {
    private int id;
    private String nume;
    private int id_diriginte;
    public enum Actiuni {create,read,update,delete}
    private String eroare="";
    private Actiuni actiune;
    private String solicitant; //pentru cand vreau sa iau din BD doar clasele la care preda un profesor
    private Boolean eDiriginte = false; //cand verific daca profesorul care a deschis catalogul e diriginte la clasa aleasa
    private Materie mat;
    int cpm;

    public Clasa(String nume) {
        this.nume = nume;
    }

    public Clasa(int id, String nume, int id_diriginte, Actiuni actiune) {
        this.id = id;
        this.nume = nume;
        this.id_diriginte = id_diriginte;
        this.actiune = actiune;
    }

    public Clasa(int id, String nume, int id_diriginte, Materie m,int cpm){
        this.id = id;
        this.nume = nume;
        this.id_diriginte = id_diriginte;
        this.mat = m;
        this.cpm=cpm;
    }

    public Clasa() {
    }

    public Boolean geteDiriginte() {
        return eDiriginte;
    }

    public void seteDiriginte(Boolean eDiriginte) {
        this.eDiriginte = eDiriginte;
    }

    public int getCpm() {
        return cpm;
    }

    public void setCpm(int cpm) {
        this.cpm = cpm;
    }

    public Materie getMat() {
        return mat;
    }

    public void setMat(Materie mat) {
        this.mat = mat;
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
