package model;

import java.io.Serializable;
import java.util.List;

public class Elev extends Logged implements Serializable {


    private String nume;
    private String prenume;
    private Clasa clasa;
    private Login solicitant;
    private Materie mat;
    private int cpm;
    private String note;
    private double medie;

    public int getCpm() {
        return cpm;
    }

    public void setCpm(int cpm) {
        this.cpm = cpm;
    }

    private int id;
    public enum Actiuni {create,read,update,delete};

    private Elev.Actiuni actiune;

    public Elev(String nume, String prenume, int id, Actiuni actiune) {
        this.nume = nume;
        this.prenume = prenume;
        this.id = id;
        this.actiune = actiune;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Materie getMat() {
        return mat;
    }

    public void setMat(Materie mat) {
        this.mat = mat;
    }

    public Clasa getClasa() {
        return clasa;
    }

    public void setClasa(Clasa clasa) {
        this.clasa = clasa;
    }

    public Login getSolicitant() {
        return solicitant;
    }

    public void setSolicitant(Login solicitant) {
        this.solicitant = solicitant;
    }

    public Elev() {
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
}
