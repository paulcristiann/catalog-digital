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
import model.Elevi;
import model.Parinte;
import model.adminClasa;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EleviController implements Initializable{

    @FXML
    TextField fNume;
    @FXML
    TextField fPrenume;
    @FXML
    TextField fEmail;
    @FXML
    public ComboBox fClasa;
    @FXML
    public ComboBox fParinte;
    @FXML
    PasswordField fParola;
    @FXML
    Label mesaj;
    @FXML
    private TableView<Elevi> table;
    @FXML
    private TableColumn<Elevi, String> nume;
    @FXML
    private TableColumn<Elevi, String> prenume;
    @FXML
    private TableColumn<Elevi, String> email;
    @FXML
    private TableColumn<Elevi, String> clasa;
    @FXML
    private TableColumn<Elevi, String> parinte;

    private ObservableList<Elevi> data;
    private ObservableList<adminClasa> clase;
    private ObservableList<Parinte> parinti;
    private Elevi inEditare;

    @FXML
    private void Mouse(MouseEvent event)
    {
        mesaj.setText("");
        Elevi obj=table.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            inEditare = obj;
            fNume.setText(obj.getNume());
            fPrenume.setText(obj.getPrenume());
            fEmail.setText(obj.getEmail());
            for (adminClasa p : clase) {
                if ( p.getId() == obj.getClasa()) {
                    fClasa.setValue(p);
                }
            }
            for (Parinte p : parinti) {
                if (p.getId() == obj.getParinte()) {
                    fParinte.setValue(p);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    public void start()
    {
        nume.setMinWidth(100);
        prenume.setMinWidth(100);
        email.setMinWidth(200);
        clasa.setMinWidth(200);
        parinte.setMinWidth(200);


        nume.setCellValueFactory(new PropertyValueFactory<Elevi, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Elevi, String>("prenume"));
        email.setCellValueFactory(new PropertyValueFactory<Elevi, String>("email"));
        clasa.setCellValueFactory(new PropertyValueFactory<Elevi,String>("numeClasa"));
        parinte.setCellValueFactory(new PropertyValueFactory<Elevi,String>("numeParinte"));

        citeste();
    }

    private void citeste() {
        Elevi e = new Elevi();
        e.setProcedura(Elevi.Proceduri.citeste);
        data = FXCollections.observableArrayList((List<Elevi>) new Send().send(e));
        table.setItems(data);

        adminClasa c = new adminClasa();
        c.setActiune(adminClasa.Actiuni.read);
        fClasa.setItems(FXCollections.observableArrayList((List<Clasa>) new Send().send(c)));

        Parinte p = new Parinte();
        p.setProcedura(Parinte.Proceduri.citeste);
        fParinte.setItems(FXCollections.observableArrayList((List<Parinte>) new Send().send(p)));
    }

    @FXML
    private void adaugare(ActionEvent event) {

        mesaj.setText("");
        Elevi e = new Elevi();
        e.setProcedura(Elevi.Proceduri.adaugare);
        e.setNume(fNume.getText());
        e.setPrenume(fPrenume.getText());
        e.setEmail(fEmail.getText());
        e.setParola(fParola.getText());
        adminClasa c=(adminClasa) fClasa.getValue();
        if(c!=null)
            e.setClasa( c.getId());
        else {
            mesaj.setText("Alegeti o clasa");
            return;
        }
        Parinte p = (Parinte) fParinte.getValue();
        if (p != null)
            e.setParinte( p.getId());
        else {
            mesaj.setText("Alegeti un parinte");
            return;
        }
        e.setProcedura(Elevi.Proceduri.adaugare);
        e = (Elevi) new Send().send(e);

        if (e.getEroare().equals("")) {
            data.add(e);
        } else {
            mesaj.setText(e.getEroare());
        }
    }
    @FXML
    private void modificare(ActionEvent event) {

        mesaj.setText("");
        if (inEditare != null) {
            Elevi e = new Elevi();
            e.setProcedura(Elevi.Proceduri.adaugare);
            e.setNume(fNume.getText());
            e.setPrenume(fPrenume.getText());
            e.setEmail(fEmail.getText());
            e.setParola(fParola.getText());
            adminClasa c=(adminClasa) fClasa.getValue();
            if(c!=null)
                e.setClasa( c.getId());
            else {
                mesaj.setText("Alegeti o clasa");
                return;
            }
            Parinte p = (Parinte) fParinte.getValue();
            if (p != null)
                e.setParinte( p.getId());
            else {
                mesaj.setText("Alegeti un parinte");
                return;
            }
            e.setProcedura(Elevi.Proceduri.modificare);
            e.setId(inEditare.getId());
            e = (Elevi) new Send().send(e);
            if (e.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
                data.add(e);
            } else {
                mesaj.setText(e.getEroare());
            }
        } else {
            mesaj.setText("Selectati un parinte din tabel");
        }
    }

    @FXML
    private void stergere(ActionEvent event) {

        mesaj.setText("");
        if (inEditare != null) {
            inEditare.setProcedura(Elevi.Proceduri.stergere);
            Elevi p = (Elevi) new Send().send(inEditare);
            if (p.getEroare().equals("")) {
                data.remove(inEditare);
                inEditare = null;
            }
        } else {
            mesaj.setText("Selectati un parinte din tabel");
        }
    }
}
