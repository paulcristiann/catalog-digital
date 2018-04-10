package Client.controller;

import Client.interfaces.ModalWindow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Nota;

public class PopUpController implements ModalWindow {


    public Label status;

    @Override
    public void setData(Object o) {
        Nota n = (Nota) o;
        if(n.getValoare() == 100)
            status.setText("Nota adaugata cu succes!");
        else
            status.setText("Eroare la adaugarea notei sau elevul are deja o teza notata!");
        System.out.println("Metoda implementata");
    }

    @FXML
    private void close() {
        Stage stage = (Stage) status.getScene().getWindow();
        stage.close();
    }

}
