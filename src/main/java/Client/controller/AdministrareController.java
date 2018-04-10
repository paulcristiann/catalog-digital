package Client.controller;

import Client.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministrareController implements Initializable {

    private Main main;
    @FXML
    private ProfesoriController includeProfesoriController;
    @FXML
    private ClaseController includeClaseController;
    @FXML
    private MateriiController includeMateriiController;
    @FXML
    private ParintiController includeParintiController;

    @FXML
    private Tab tabProfesori;


    public void setApp(Main application) {
        this.main = application;
    }

    public void logout() {
        main.userLogout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        includeProfesoriController.start();

    }

    public void refreshClase() {
        includeClaseController.start();
    }

    public void refreshProfesori() {
        includeProfesoriController.start();
    }

    public void refreshMaterii() {
        includeMateriiController.start();
    }

    public void refreshParinti() {
        includeParintiController.start();
    }


}