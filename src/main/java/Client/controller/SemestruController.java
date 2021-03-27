package Client.controller;

import Client.Send;
import Client.aspects.Loggable;
import Server.ParintiWebController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Semestru;

import javax.swing.plaf.basic.BasicButtonUI;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Server.db.getCon;

public class SemestruController implements Initializable {
    @FXML
    private Label semestru;
    @FXML
    private Button comuta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Loggable
    public void start() {

        if (getSemestrulCurent() == 1) {
            Platform.runLater(() ->  semestru.setText("Semestrul I"));
        } else {
            Platform.runLater(() ->  semestru.setText("Semestrul II"));
            Platform.runLater(() ->  comuta.setVisible(false));
        }

    }

    @Loggable
    public void comutaSem() {
        setSemestrulCurent(2);
        start();
    }

    @Loggable
    private void setSemestrulCurent(int semestru) {
        Semestru s = new Semestru();
        s.setGet(false);
        s.setSemestru(semestru);
        Send.send(s);
    }

    @Loggable
    public static int getSemestrulCurent() {
        Semestru s = (Semestru) Send.send(new Semestru());
        return s.getSemestru();
    }

}
