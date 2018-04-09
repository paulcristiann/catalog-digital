package Client.controller;

import Client.Send;
import Client.interfaces.ModalWindow;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.*;

public class NoteazaElev implements ModalWindow {

    public javafx.scene.control.Label numeElev;
    public ComboBox campN;
    private Elev elev;
    private Login profesor;

    public boolean add(){

        System.out.println("Nota trebuie adaugata!");
        if(campN.getSelectionModel().getSelectedIndex() > -1)
        {
            //urmeaza inregistrarea notei in BD
            Nota n = new Nota((int) campN.getSelectionModel().getSelectedItem());
            n.setActiune(Nota.Actiuni.create);
            n.setElev(elev);
            n.setProfesor(elev.getSolicitant());
            n.setMaterie(elev.getMat());
            n.setClasa(elev.getClasa());
            try {
                n = (Nota) new Send().send(n);
            }catch (Exception e){
                System.out.println(e);
            }
            new Client.ModalWindow("/Client/view/PopUp.fxml", n);
            close();
            return true;
        }
        else
        {
            numeElev.setText("Nu ati selectat o nota");
            return false;
        }
    }


    @Override
    public void setData(Object o) {

        Elev e = (Elev) o;
        numeElev.setText(e.getNume() + " " + e.getPrenume());
        campN.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10));
        elev = e;
        System.out.println("Metoda implementata");
    }

    private void close() {
        Stage stage = (Stage) numeElev.getScene().getWindow();
        stage.close();
    }
}
