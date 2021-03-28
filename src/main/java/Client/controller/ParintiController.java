package Client.controller;

import Client.aspects.Loggable;
import javafx.fxml.Initializable;
import Client.ModalWindow;
import Client.Send;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Parinte;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ParintiController implements Initializable {
    @FXML
    TextField fNume;
    @FXML
    TextField fPrenume;
    @FXML
    TextField fEmail;
    @FXML
    TextField fTelefon;
    @FXML
    PasswordField fParola;
    @FXML
    Label mesaj;
    @FXML
    private TableView<Parinte> table;
    @FXML
    private TableColumn<Parinte, String> nume;
    @FXML
    private TableColumn<Parinte, String> prenume;
    @FXML
    private TableColumn<Parinte, String> email;
    @FXML
    private TableColumn<Parinte, String> telefon;

    private ObservableList<Parinte> data;
    private Parinte inEditare;

    @FXML
    private void Mouse(MouseEvent event) {
        mesaj.setText("");
        Parinte obj = table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            inEditare = obj;
            fNume.setText(obj.getNume());
            fPrenume.setText(obj.getPrenume());
            fEmail.setText(obj.getEmail());
            fTelefon.setText(obj.getTelefon());

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Loggable
    public void start(){

        nume.setMinWidth(100);
        prenume.setMinWidth(100);
        email.setMinWidth(200);
        telefon.setMinWidth(200);


        nume.setCellValueFactory(new PropertyValueFactory<Parinte, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Parinte, String>("prenume"));
        email.setCellValueFactory(new PropertyValueFactory<Parinte, String>("email"));
        telefon.setCellValueFactory(new PropertyValueFactory<Parinte,String>("telefon"));

        citeste();
    }

    @Loggable
    private void citeste() {
        Parinte p = new Parinte();
        p.setProcedura(Parinte.Proceduri.citeste);

        data = FXCollections.observableArrayList((List<Parinte>) new Send().send(p));
        table.setItems(data);
    }

    @FXML
    @Loggable
    private void adaugare(ActionEvent event) {

        mesaj.setText("");
        Parinte p = new Parinte(fNume.getText(), fPrenume.getText(), fEmail.getText(), fParola.getText(),fTelefon.getText());
        p.setProcedura(Parinte.Proceduri.adaugare);
        p = (Parinte) new Send().send(p);
        System.out.println(p.getPrenume());

        if (p.getEroare().equals("")) {
            data.add(p);
        } else {
            mesaj.setText(p.getEroare());
        }
    }

    @FXML
    @Loggable
    private void modificare(ActionEvent event) {

        mesaj.setText("");
        if (inEditare != null) {
            Parinte p = new Parinte(fNume.getText(), fPrenume.getText(), fEmail.getText(), fParola.getText(),fTelefon.getText());
            p.setProcedura(Parinte.Proceduri.modificare);
            p.setId(inEditare.getId());
            p = (Parinte) new Send().send(p);
            if (p.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
                data.add(p);
            } else {
                mesaj.setText(p.getEroare());
            }
        } else {
            mesaj.setText("Selectati un parinte din tabel");
        }
    }

    @FXML
    @Loggable
    private void stergere(ActionEvent event) {

        mesaj.setText("");
        if (inEditare != null) {
            inEditare.setProcedura(Parinte.Proceduri.stergere);
            Parinte p = (Parinte) new Send().send(inEditare);
            if (p.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
            }
        } else {
            mesaj.setText("Selectati un parinte din tabel");
        }
    }

    }


