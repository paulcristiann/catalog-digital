package model;

import java.io.Serializable;

public class Login implements Serializable {
    private String user;
    private String password;
    private String token;
    private Boolean administrare;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Boolean logged;

    public Login() {
    }

    public Login(String nume, String password) {
        this.user = nume;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    public Boolean isLogged() {
        return logged;
    }

    public Boolean getAdministrare() {
        return administrare;
    }

    public void setAdministrare(Boolean administrare) {
        this.administrare = administrare;
    }
}
