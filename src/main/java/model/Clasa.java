package model;

import java.io.Serializable;

public class Clasa implements Serializable {
    private int id;
    private String nume;
    private int id_diriginte=-1;//pentru a verifica daca a fost sau nu selectat un diriginte pentru clasa
    private String eroare="";
    public enum Actiuni {create,read,update,delete};
    private Actiuni actiune;

    private String diriginte;

    public String getDiriginte() {
        return diriginte;
    }

    public void setDiriginte(String diriginte) {
        this.diriginte = diriginte;
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
}
