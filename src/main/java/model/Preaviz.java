package model;

import java.io.Serializable;

public class Preaviz extends Logged implements Serializable{
    private String nume;
    private String prenume;
    private int id;
    private int NrAbsente;
    public enum Actiuni{verificare}
    private Actiuni actiune;


    private int diriginte;

    public Preaviz(){}
    public Actiuni getActiune() {
        return actiune;
    }

    public int getDiriginte() {
        return diriginte;
    }

    public void setDiriginte(int diriginte) {
        this.diriginte = diriginte;
    }

    public void setActiune(Actiuni actiune) {
        this.actiune = actiune;
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

    public int getNrAbsente() {
        return NrAbsente;
    }

    public void setNrAbsente(int nrAbsente) {
        NrAbsente = nrAbsente;
    }

    public Preaviz(String nume,String prenume,int id,int nrAbsente){
        this.id = id;
        this.NrAbsente = nrAbsente;
        this.nume = nume;
        this.prenume = prenume;
    }

    @Override
    public String toString() {
        return "Preaviz{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", id=" + id +
                ", NrAbsente=" + NrAbsente +
                '}';
    }
}
