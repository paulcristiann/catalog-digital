package model;

import java.io.Serializable;

public class Elevi extends Logged implements Serializable {
    private int id;
    private String nume;
    private String prenume;
    private String email;
    private String token;
    private String parola;
    private String eroare = "";
    private int clasa;
    private String numeclasa;
    private String numeparinte;
    private int parinte;
    public enum Proceduri {adaugare,modificare,stergere,citeste}
    private Proceduri procedura;

    public Elevi(String nume,String prenume,String email,String parola,int clasa,int parinte) {
        this.nume=nume;
        this.prenume=prenume;
        this.email=email;
        this.parola=parola;
        this.clasa = clasa;
        this.parinte = parinte;
    }

    public Elevi(){
    }
    public Elevi(int id,String nume,String prenume,String email,String token,String parola,int clasa,int parinte) {

        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.token = token;
        this.parola = parola;
        this.clasa = clasa;
        this.parinte = parinte;
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

    public int getParinte() { return parinte; }
    public void setParinte(int parinte) { this.parinte= parinte; }

    public int getClasa() { return clasa; }

    public void setClasa(int clasa) { this.clasa = clasa; }

    public String getNumeClasa() { return numeclasa; }

    public void setNumeClasa(String numeclasa) { this.numeclasa = numeclasa; }

    public String getNumeParinte() { return numeparinte; }

    public void setNumeParinte(String  numeparinte) { this.numeparinte = numeparinte; }

    public Proceduri getProcedura() { return procedura; }
    public void setProcedura(Proceduri procedura) { this.procedura = procedura; }

    @Override
    public String toString() { return nume +" " + prenume;  }

}
