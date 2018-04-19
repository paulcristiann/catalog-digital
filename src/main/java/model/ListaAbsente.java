package model;

import java.io.Serializable;

public class ListaAbsente extends Logged implements Serializable {
    private String materie;
    private String data;
    private int id;
    public enum Actiune{afisare}
    private Actiune a;

    public Actiune getA() {
        return a;
    }

    public void setA(Actiune a) {
        this.a = a;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListaAbsente(){}


}
