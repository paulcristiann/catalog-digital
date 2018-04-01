package Client.controller;

import Client.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Clasa;
import model.Login;
import model.Profesor;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogController implements Initializable {

    public javafx.scene.control.Label clasa;
    public javafx.scene.control.Label prof;


    private Main main;

    private Clasa clasaDeschisa;

    private Login profesorLogat;

    public Clasa getClasaDeschisa() {
        return clasaDeschisa;
    }

    public void setClasaDeschisa(Clasa clasaDeschisa) {
        this.clasaDeschisa = clasaDeschisa;
    }

    public Login getProfesorLogat() {
        return profesorLogat;
    }

    public void setProfesorLogat(Login profesorLogat) {
        this.profesorLogat = profesorLogat;
    }

    public void setApp(Main application) {
        this.main = application;
        clasa.setText(clasaDeschisa.toString());
        prof.setText(profesorLogat.getUser());
    }

    public void logout() {
        main.userLogout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


}
