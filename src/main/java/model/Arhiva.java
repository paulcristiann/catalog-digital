package model;

import java.io.Serializable;

public class Arhiva extends Logged implements Serializable {

    private String arhiva;
    private String eroare;

    public String getEroare() {
        return eroare;
    }

    public void setEroare(String eroare) {
        this.eroare = eroare;
    }

    public String getArhiva() {
        return arhiva;
    }

    public void setArhiva(String arhiva) {
        this.arhiva = arhiva;
    }
}
