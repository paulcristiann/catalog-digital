package model;

import java.io.Serializable;

public class Parinte  extends Logged implements Serializable {

    private int id;
    private String nume;
    private String prenume;
    private String email;
    private String token;
    private String parola;
    private String eroare = "";
    private String telefon;
    public enum Proceduri {adaugare,modificare,stergere,citeste}
    private Proceduri procedura;

    public Parinte(String nume,String prenume,String email,String parola,String telefon) {
        this.nume=nume;
        this.prenume=prenume;
        this.email=email;
        this.parola=parola;
        this.telefon = telefon;
          }
    public Parinte(){}
    public Parinte(int id,String nume,String prenume,String email,String token,String parola,String telefon) {

        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.token = token;
        this.parola = parola;
        this.telefon = telefon;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }


    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    @Override
    public String getToken() { return token; }
    @Override
    public void setToken(String token) { this.token = token; }


    public String getParola() { return parola; }
    public void setParola(String parola) { this.parola = parola; }


    public String getEroare() { return eroare; }
    public void setEroare(String eroare) { this.eroare = eroare; }

    public String getTelefon() { return telefon; }

    public void setTelefon(String telefon) { this.telefon = telefon; }

    public Proceduri getProcedura() { return procedura; }
    public void setProcedura(Proceduri procedura) { this.procedura = procedura; }

    @Override
    public String toString() { return nume +" " + prenume;  }

}
