package model;

import java.io.Serializable;

public abstract class Logged implements Serializable {
    String token;
    Boolean administrare;

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
