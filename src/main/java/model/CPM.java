package model;

import java.io.Serializable;

public class CPM extends Logged implements Serializable {
    private int idClasa;
    private int idProfesor;
    private int idMaterie;
    private int id;
    private String numeClasa;
    private String numeProfesor;
    private String numeMaterie;
    private String eroare="";

    public enum Actiuni {create, read, update, delete, inlocuieste}


    private Actiuni actiune;

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

    public int getIdClasa() {
        return idClasa;
    }

    public void setIdClasa(int idClasa) {
        this.idClasa = idClasa;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdMaterie() {
        return idMaterie;
    }

    public void setIdMaterie(int idMaterie) {
        this.idMaterie = idMaterie;
    }

    public String getNumeClasa() {
        return numeClasa;
    }

    public void setNumeClasa(String numeClasa) {
        this.numeClasa = numeClasa;
    }

    public String getNumeProfesor() {
        return numeProfesor;
    }

    public void setNumeProfesor(String numeProfesor) {
        this.numeProfesor = numeProfesor;
    }

    public String getNumeMaterie() {
        return numeMaterie;
    }

    public void setNumeMaterie(String numeMaterie) {
        this.numeMaterie = numeMaterie;
    }
}
