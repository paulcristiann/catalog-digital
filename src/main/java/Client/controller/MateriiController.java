package Client.controller;

import Client.Send;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Materie;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MateriiController implements Initializable {

    @FXML
    TextField fMaterie;
    @FXML
    Label mesaj;

    @FXML
    private TableView<Materie> table;
    @FXML
    private TableColumn<Materie, String> materie;

    private ObservableList<Materie> data;
    private Materie inEditare;


    @FXML
    private void Mouse(MouseEvent event) {
        mesaj.setText("");
        Materie obj = table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            inEditare = obj;
            fMaterie.setText(obj.getNume());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        materie.setCellValueFactory(new PropertyValueFactory<Materie, String>("nume"));

        Materie m = new Materie();
        m.setActiune(Materie.Actiuni.read);
        data = FXCollections.observableArrayList((List<Materie>) new Send().send(m));
        table.setItems(data);

    }

    @FXML
    private void add(ActionEvent event) {
        mesaj.setText("");
        Materie m = new Materie(fMaterie.getText());
        m.setActiune(Materie.Actiuni.create);
        m = (Materie) new Send().send(m);
        if (m.getEroare().equals("")) {
            data.add(m);
        } else {
            mesaj.setText(m.getEroare());
        }
    }

    @FXML
    private void update(ActionEvent event) {
        mesaj.setText("");
        if (inEditare != null) {
            Materie m = new Materie(fMaterie.getText());
            m.setActiune(Materie.Actiuni.update);
            m.setId(inEditare.getId());
            m = (Materie) new Send().send(m);
            if (m.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
                data.add(m);
            } else {
                mesaj.setText(m.getEroare());
            }
        } else {
            mesaj.setText("Selectati o materie din tabel");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        mesaj.setText("");
        if (inEditare != null) {
            inEditare.setActiune(Materie.Actiuni.delete);
            Materie m = (Materie) new Send().send(inEditare);
            if (m.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
            } else {
                mesaj.setText(inEditare.getEroare());
            }
        } else {
            mesaj.setText("Selectati  o materie din tabel");
        }
    }
}


