package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Profesori implements Serializable {
    private ArrayList<Profesor> profesori;

    public ArrayList<Profesor> getProfesori() {
        return profesori;
    }

    public void setProfesori(ArrayList<Profesor> profesori) {
        this.profesori = profesori;
    }

}
