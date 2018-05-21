package model;

import com.mysql.jdbc.log.Log;

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

    private String data;

    private int id;

    public enum Actiuni {create, read, update, delete, teza, readAbsente, motiveazaAbsente, readNoteMaterie, returnCPM};

    private Nota.Actiuni actiune;

    public Nota(Login pl,Clasa cd){
        profesor = pl;
        clasa = cd;
    }

    public Nota(int nr) {

        this.valoare = nr;

    }

    public Nota(String d, int n, int id) {
        data = d;
        valoare = n;
        this.id = id;
    }

    public Nota(Elev e)
    {
        elev = e;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if(valoare == -1 && data != null){
            return data;
        }
        else{
            return String.valueOf(valoare);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
