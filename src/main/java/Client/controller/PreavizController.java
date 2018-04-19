package Client.controller;

import Client.ModalWindow;
import Client.Send;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import model.Preaviz;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PreavizController implements Initializable {

    @FXML
    private TableView<Preaviz> table;
    @FXML
    private TableColumn<Preaviz, String> nume;
    @FXML
    private TableColumn<Preaviz, String> prenume;
    @FXML
    private TableColumn<Preaviz, String> nrAbsente;
    private Preaviz inEditare;
    private ObservableList<Preaviz> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @FXML
    private void Mouse(MouseEvent event) {
        Preaviz obj = table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            new ModalWindow("/Client/view/ListaAbsente.fxml",obj);
        }
    }

    public void start(){

        nume.setMinWidth(100);
        prenume.setMinWidth(100);
        nrAbsente.setMinWidth(200);

        nume.setCellValueFactory(new PropertyValueFactory<Preaviz, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Preaviz, String>("prenume"));
        nrAbsente.setCellValueFactory(new PropertyValueFactory<Preaviz, String>("NrAbsente"));

        Preaviz p = new Preaviz();
        p.setActiune(Preaviz.Actiuni.verificare);
        data = FXCollections.observableArrayList((List<Preaviz>) new Send().send(p));
        table.setItems(data);

    }
}
