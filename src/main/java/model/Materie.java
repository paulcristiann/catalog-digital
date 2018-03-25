package model;

import java.io.Serializable;

public class Materie implements Serializable {
    private String materie;
    private int id;
    private String eroare="";
    public enum Actiuni {create,read,update,delete};
    private Actiuni actiune;

    public Materie(String materie, int id) {
        this.materie = materie;
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

    public Materie(String materie) {
        this.materie = materie;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
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
