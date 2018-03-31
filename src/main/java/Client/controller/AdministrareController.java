package Client.controller;

import Client.Main;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministrareController implements Initializable {

    private Main main;


    public void setApp(Main application) {
        this.main = application;
    }

    public void logout() {
        main.userLogout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}