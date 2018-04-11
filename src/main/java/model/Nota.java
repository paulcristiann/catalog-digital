package model;

import java.io.Serializable;

public class Nota extends Logged implements Serializable {
    private int cpm;

    public int getCpm() {
        return cpm;
    }

    public void setCpm(int cpm) {
        this.cpm = cpm;
    }

    private int valoare;

    private Elev elev;

    private Login profesor;

    private Materie materie;

    private Clasa clasa;

    private int semestru;

    public enum Actiuni {create, read, update, delete, teza}

    ;

    private Nota.Actiuni actiune;

    public Nota(int nr) {

        this.valoare = nr;

    }

    public int getSemestru() {
        return semestru;
    }

    public void setSemestru(int semestru) {
        this.semestru = semestru;
    }

    public Elev getElev() {
        return elev;
    }

    public void setElev(Elev elev) {
        this.elev = elev;
    }

    public Login getProfesor() {
        return profesor;
    }

    public void setProfesor(Login profesor) {
        this.profesor = profesor;
    }

    public Materie getMaterie() {
        return materie;
    }

    public void setMaterie(Materie materie) {
        this.materie = materie;
    }

    public Clasa getClasa() {
        return clasa;
    }

    public void setClasa(Clasa clasa) {
        this.clasa = clasa;
    }

    public int getValoare() {
        return valoare;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    public Nota.Actiuni getActiune() {
        return actiune;
    }

    public void setActiune(Nota.Actiuni actiune) {
        this.actiune = actiune;
    }
}
