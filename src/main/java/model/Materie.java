package model;

import java.io.Serializable;

public class Materie extends Logged implements Serializable {
    private String nume;
    private int id;
    private String eroare="";
    public enum Actiuni {create,read,update,delete};
    private Actiuni actiune;

    public Materie(String nume, int id) {
        this.nume = nume;
        this.id = id;
    }

    public Actiuni getActiune() {
        return actiune;
    }

    public void setActiune(Actiuni actiune) {
        this.actiune = actiune;
    }

    public Materie(){

    }

    public Materie(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEroare() {
        return eroare;
    }

    public void setEroare(String eroare) {
        this.eroare = eroare;
    }
}
