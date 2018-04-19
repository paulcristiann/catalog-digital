package Client.controller;

import Client.Send;
import Client.interfaces.ModalWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import model.Elev;
import model.Nota;

import java.util.ArrayList;
import java.util.List;

public class MotiveazaController implements ModalWindow {


    public Label numeElev;
    public Label nrAbsente;
    public ListView campAbsente;
    private Elev elev;

    @Override
    public void setData(Object o) {

        Elev e = (Elev) o;
        numeElev.setText(e.getNume() + " " + e.getPrenume());
        elev = e;
        Nota n = new Nota(80);
        n.setActiune(Nota.Actiuni.readAbsente);
        n.setElev(e);
        List<Nota> abs = (List<Nota>) new Send().send(n);
        campAbsente.setItems(FXCollections.observableArrayList(abs));
        campAbsente.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nrAbsente.setText("" + abs.size());
    }

    private void close() {
        Stage stage = (Stage) numeElev.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void motiveazaAbsente(){

        ObservableList<Nota> absente = campAbsente.getSelectionModel().getSelectedItems();

        for (Nota n: absente){

            n.setActiune(Nota.Actiuni.motiveazaAbsente);
            Nota x = (Nota) new Send().send(n);
            close();
            System.out.println(x.getValoare());
        }

    }

}
