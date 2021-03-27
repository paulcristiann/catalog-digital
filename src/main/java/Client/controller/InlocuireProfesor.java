package Client.controller;

import Client.Send;
import Client.aspects.Loggable;
import Client.interfaces.ModalWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Profesor;

import java.util.List;


public class InlocuireProfesor implements ModalWindow {

    @FXML
    private Label mesaj;
    @FXML
    private Label err;
    @FXML
    private ComboBox inlocuitor;
    @FXML
    private Button anulare;
    private Profesor p;

    @Override
    @Loggable
    public void setData(Object o) {
        p = (Profesor) o;
        mesaj.setText("Pentru a putea sterge profesorul " + p.getNume() + " " + p.getPrenume() +
                "\ntrebuie sa il inlocuiti.\n Selectati un profesor inlocuitor");

        Profesor p2 = new Profesor();
        p2.setActiune(Profesor.Actiuni.read);
        ObservableList<Profesor> profesori = FXCollections.observableArrayList((List<Profesor>) new Send().send(p2));
        inlocuitor.setItems(profesori);

    }

    @FXML
    private void anuleaza(ActionEvent ae) {
        close();
    }

    @FXML
    @Loggable
    private void sterge(ActionEvent ae) {
        if (inlocuitor.getValue() == null) {
            err.setText("Selectati un inlocuitor");
        } else {
            p.setActiune(Profesor.Actiuni.inlocuieste);
            p.setInlocuitor(((Profesor) inlocuitor.getValue()).getId());
            Profesor p2 = (Profesor) new Send().send(p);
            if (p2.getEroare().equals("")) {
                //stergem profesorul
                p.setActiune(Profesor.Actiuni.delete);
                p2 = (Profesor) new Send().send(p);
                if (p2.getEroare().equals("")) {
                    close();
                } else {
                    err.setText(p2.getEroare());
                }
            } else {
                err.setText(p2.getEroare());
            }
        }
    }

    @FXML
    @Loggable
    private void inlocuieste(ActionEvent ae) {
        if (inlocuitor.getValue() == null) {
            err.setText("Selectati un inlocuitor");
        } else {
            p.setActiune(Profesor.Actiuni.inlocuieste);
            p.setInlocuitor(((Profesor) inlocuitor.getValue()).getId());
            Profesor p2 = (Profesor) new Send().send(p);
            if (p2.getEroare().equals("")) {
                close();
            } else {
                err.setText(p2.getEroare());
            }
        }
    }

    private void close() {
        Stage stage = (Stage) anulare.getScene().getWindow();
        stage.close();
    }
}
