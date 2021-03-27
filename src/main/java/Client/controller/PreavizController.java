package Client.controller;

import Client.ModalWindow;
import Client.Send;
import Client.aspects.Loggable;
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
import model.ListaAbsente;
import model.Preaviz;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PreavizController implements Client.interfaces.ModalWindow{



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


    @FXML
    private void Mouse(MouseEvent event) {
        Preaviz obj = table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            new ModalWindow("/Client/view/ListaAbsente.fxml",obj);
        }
    }

    @Override
    @Loggable
    public void setData(Object o) {

        nume.setMinWidth(100);
        prenume.setMinWidth(100);
        nrAbsente.setMinWidth(200);

        nume.setCellValueFactory(new PropertyValueFactory<Preaviz, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Preaviz, String>("prenume"));
        nrAbsente.setCellValueFactory(new PropertyValueFactory<Preaviz, String>("NrAbsente"));


        data = FXCollections.observableArrayList((List<Preaviz>)o);
        table.setItems(data);


    }

    @Loggable
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
