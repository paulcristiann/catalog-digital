package model;

import java.io.Serializable;

public abstract class Logged implements Serializable {
    String token;
    Boolean administrare;
    int codEroare=0;

    public int getCodEroare() {
        return codEroare;
    }

    public void setCodEroare(int codEroare) {
        this.codEroare = codEroare;
    }



    public Boolean getAdministrare() {
        return administrare;
    }

    public void setAdministrare(Boolean administrare) {
        this.administrare = administrare;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
