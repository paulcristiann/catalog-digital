package Client.controller;

import Client.Send;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CPM;
import model.Materie;
import model.Profesor;
import model.adminClasa;

import java.util.List;

public class CPMController {

    @FXML
    ComboBox fclasa;
    @FXML
    ComboBox fprofesor;
    @FXML
    ComboBox fmaterie;

    @FXML
    Label mesaj;

    @FXML
    private TableView<CPM> table;
    @FXML
    private TableColumn<CPM, String> clasa;
    @FXML
    private TableColumn<CPM, String> profesor;
    @FXML
    private TableColumn<CPM, String> materie;


    private ObservableList<CPM> data;
    private ObservableList<adminClasa> clase;
    private ObservableList<Profesor> profesori;
    private ObservableList<Materie> materii;
    private CPM inEditare;


    @FXML
    private void Mouse(MouseEvent event) {
        mesaj.setText("");
        CPM obj = table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            inEditare = obj;


            for (adminClasa c : clase) {
                if (c.getId() == obj.getIdClasa()) {
                    fclasa.setValue(c);
                }
            }
            for (Profesor p : profesori) {
                if (p.getId() == obj.getIdProfesor()) {
                    fprofesor.setValue(p);
                }
            }
            for (Materie m : materii) {
                if (m.getId() == obj.getIdMaterie()) {
                    fmaterie.setValue(m);
                }
            }
        }
    }


    public void start() {
        clasa.setCellValueFactory(new PropertyValueFactory<CPM, String>("numeClasa"));
        profesor.setCellValueFactory(new PropertyValueFactory<CPM, String>("numeProfesor"));
        materie.setCellValueFactory(new PropertyValueFactory<CPM, String>("numeMaterie"));

        CPM cpm = new CPM();
        cpm.setActiune(CPM.Actiuni.read);
        data = FXCollections.observableArrayList((List<CPM>) new Send().send(cpm));
        table.setItems(data);

        adminClasa c = new adminClasa();
        c.setActiune(adminClasa.Actiuni.read);
        clase = FXCollections.observableArrayList((List<adminClasa>) new Send().send(c));
        Platform.runLater(() -> fclasa.setItems(clase));

        Profesor p = new Profesor();
        p.setActiune(Profesor.Actiuni.read);
        profesori = FXCollections.observableArrayList((List<Profesor>) new Send().send(p));
        Platform.runLater(() -> fprofesor.setItems(profesori));

        Materie m = new Materie();
        m.setActiune(Materie.Actiuni.read);
        materii = FXCollections.observableArrayList((List<Materie>) new Send().send(m));
        Platform.runLater(() -> fmaterie.setItems(materii));
    }

    @FXML
    private void add(ActionEvent event) {
        mesaj.setText("");
        CPM cpm = new CPM();
        cpm.setActiune(CPM.Actiuni.create);

        adminClasa c = (adminClasa) fclasa.getValue();
        if (c != null) {
            cpm.setIdClasa(c.getId());
            cpm.setNumeClasa(c.getNume());
        } else {
            mesaj.setText("Alegeti o clasa");
            return;
        }
        Profesor p = (Profesor) fprofesor.getValue();
        if (p != null) {
            cpm.setIdProfesor(p.getId());
            cpm.setNumeProfesor(p.getNume() + " " + p.getPrenume());
        } else {
            mesaj.setText("Alegeti un profesor");
            return;
        }
        Materie m = (Materie) fmaterie.getValue();
        if (m != null) {
            cpm.setIdMaterie(m.getId());
            cpm.setNumeMaterie(m.getNume());
        } else {
            mesaj.setText("Alegeti o materie");
            return;
        }
        cpm = (CPM) new Send().send(cpm);
        if (cpm.getEroare().equals("")) {
            data.add(cpm);
        } else {
            mesaj.setText(cpm.getEroare());
        }
    }


    @FXML
    private void delete(ActionEvent event) {
        mesaj.setText("");
        if (inEditare != null) {
            inEditare.setActiune(CPM.Actiuni.delete);
            CPM cpm = (CPM) new Send().send(inEditare);
            if (cpm.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
            } else {
                mesaj.setText(cpm.getEroare());
            }
        } else {
            mesaj.setText("Selectati  ");
        }
    }
}
