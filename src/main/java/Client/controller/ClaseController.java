package Client.controller;

import Client.Send;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Clasa;
import model.Profesor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClaseController implements Initializable {

    public ComboBox fdiriginte;
    @FXML
    TextField fclasa;
    @FXML
    Label mesaj;

    @FXML
    private TableView<Clasa> table;
    @FXML
    private TableColumn<Clasa, String> clasa;
    @FXML
    private TableColumn<Clasa, String> diriginte;


    private ObservableList<Clasa> data;
    private ObservableList<Profesor> profesori;
    private Clasa inEditare;


    @FXML
    private void Mouse(MouseEvent event) {
        mesaj.setText("");
        Clasa obj = table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            inEditare = obj;
            fclasa.setText(obj.getNume());

            for (Profesor p : profesori) {
                if (p.getId() == obj.getId_diriginte()) {
                    fdiriginte.setValue(p);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clasa.setCellValueFactory(new PropertyValueFactory<Clasa, String>("nume"));
        diriginte.setCellValueFactory(new PropertyValueFactory<Clasa, String>("diriginte"));

        Clasa c = new Clasa();
        c.setActiune(Clasa.Actiuni.read);
        data = FXCollections.observableArrayList((List<Clasa>) new Send().send(c));
        table.setItems(data);

        Profesor p = new Profesor();
        p.setActiune(Profesor.Actiuni.read);
        profesori = FXCollections.observableArrayList((List<Profesor>) new Send().send(p));
        fdiriginte.setItems(profesori);
    }

    @FXML
    private void add(ActionEvent event) {
        mesaj.setText("");
        Clasa c = new Clasa();
        c.setActiune(Clasa.Actiuni.create);
        c.setNume(fclasa.getText());
        Profesor p = (Profesor) fdiriginte.getValue();
        if (p != null)
            c.setId_diriginte(p.getId());
        else {
            mesaj.setText("Alegeti un diriginte");
            return;
        }
        c = (Clasa) new Send().send(c);
        if (c.getEroare().equals("")) {
            data.add(c);
        } else {
            mesaj.setText(c.getEroare());
        }
    }

    @FXML
    private void update(ActionEvent event) {
        mesaj.setText("");
        if (inEditare != null) {
            Clasa c = new Clasa();
            c.setActiune(Clasa.Actiuni.update);
            c.setId(inEditare.getId());
            c.setNume(fclasa.getText());
            Profesor p = (Profesor) fdiriginte.getValue();
            if (p != null)
                c.setId_diriginte(p.getId());
            else {
                mesaj.setText("Alegeti un diriginte");
                return;
            }
            c = (Clasa) new Send().send(c);
            if (c.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
                data.add(c);
            } else {
                mesaj.setText(c.getEroare());
            }
        } else {
            mesaj.setText("Selectati o clasa din tabel");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        mesaj.setText("");
        if (inEditare != null) {
            inEditare.setActiune(Clasa.Actiuni.delete);
            Clasa c = (Clasa) new Send().send(inEditare);
            if (c.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
            } else {
                mesaj.setText(inEditare.getEroare());
            }
        } else {
            mesaj.setText("Selectati  o clasa din tabel");
        }
    }
}


