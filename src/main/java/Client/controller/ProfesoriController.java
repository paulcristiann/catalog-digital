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
import model.Profesor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfesoriController implements Initializable {

    @FXML
    TextField fNume;
    @FXML
    TextField fPrenume;
    @FXML
    TextField fEmail;
    @FXML
    PasswordField fParola;
    @FXML
    Label mesaj;
    @FXML
    private Label label;
    @FXML
    private TableView<Profesor> table;
    @FXML
    private TableColumn<Profesor, String> nume;
    @FXML
    private TableColumn<Profesor, String> prenume;
    @FXML
    private TableColumn<Profesor, String> email;
    private ObservableList<Profesor> data;
    private Profesor inEditare;


    @FXML
    private void Mouse(MouseEvent event) {
        mesaj.setText("");
        Profesor obj = table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            inEditare = obj;
            fNume.setText(obj.getNume());
            fPrenume.setText(obj.getPrenume());
            fEmail.setText(obj.getEmail());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nume.setMinWidth(100);
        prenume.setMinWidth(100);
        email.setMinWidth(200);


        nume.setCellValueFactory(new PropertyValueFactory<Profesor, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Profesor, String>("prenume"));
        email.setCellValueFactory(new PropertyValueFactory<Profesor, String>("email"));
        Profesor p = new Profesor();
        p.setActiune(Profesor.Actiuni.read);

        data = FXCollections.observableArrayList((ArrayList<Profesor>)new Send().send(p));
        table.setItems(data);

    }

    @FXML
    private void add(ActionEvent event) {
        mesaj.setText("");
        Profesor p = new Profesor(fNume.getText(), fPrenume.getText(), fEmail.getText(), fParola.getText());
        p.setActiune(Profesor.Actiuni.create);
        p = (Profesor) new Send().send(p);
        if (p.getEroare().equals("")) {
            data.add(p);
        } else {
            mesaj.setText(p.getEroare());
        }
    }

    @FXML
    private void update(ActionEvent event) {
        mesaj.setText("");
        if (inEditare != null) {
            Profesor p = new Profesor(fNume.getText(), fPrenume.getText(), fEmail.getText(), fParola.getText());
            p.setActiune(Profesor.Actiuni.update);
            p.setId(inEditare.getId());
            p = (Profesor) new Send().send(p);
            if (p.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
                data.add(p);
            } else {
                mesaj.setText(p.getEroare());
            }
        } else {
            mesaj.setText("Selectati un profesor din tabel");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        mesaj.setText("");
        if (inEditare != null) {
            inEditare.setActiune(Profesor.Actiuni.delete);
            Profesor p = (Profesor) new Send().send(inEditare);
            if (p.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
            } else {
                mesaj.setText(inEditare.getEroare());
            }
        } else {
            mesaj.setText("Selectati un profesor din tabel");
        }
    }
}
