package Client.controller;

import Client.Main;
import Client.Send;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import model.Clasa;
import model.Elev;
import model.Login;
import model.Nota;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CatalogController implements Initializable {

    public javafx.scene.control.Label clasa;
    public javafx.scene.control.Label prof;
    public javafx.scene.control.Label eroare;
    public Button butonMotivare;

    @FXML
    private TableView<Elev> elevi;
    @FXML
    private TableColumn<Elev, String> nume;
    @FXML
    private TableColumn<Elev, String> prenume;
    @FXML
    private TableColumn<Elev, String> note;
    @FXML
    private FlowPane loading;

    private ObservableList<Elev> data;


    private Main main;

    private Clasa clasaDeschisa;

    private Login profesorLogat;

    private Elev inEditare;

    public Clasa getClasaDeschisa() {
        return clasaDeschisa;
    }

    public void setClasaDeschisa(Clasa clasaDeschisa) {
        this.clasaDeschisa = clasaDeschisa;
    }

    public Login getProfesorLogat() {
        return profesorLogat;
    }

    public void setProfesorLogat(Login profesorLogat) {
        this.profesorLogat = profesorLogat;
    }

    @FXML
    private void Mouse(MouseEvent event) {
        //mesaj.setText("");
        Elev obj = elevi.getSelectionModel().selectedItemProperty().get();
        if (obj != null) {
            inEditare = obj;
            System.out.println("Selectat: " + obj.getNume());
        }
    }

    public void setApp(Main application) {
        this.main = application;
        clasa.setText(clasaDeschisa.toString() + ", materia: " + clasaDeschisa.getMat().getNume() + " (Semestrul "
                + SemestruController.getSemestrulCurent() + ")");
        if(clasaDeschisa.geteDiriginte()){

            prof.setText(profesorLogat.getUser() + ". Sunteti diriginte, puteti motiva absente.");

        }
        else{
            prof.setText(profesorLogat.getUser() + ". Nu sunteti diriginte, nu puteti motiva absente.");
            butonMotivare.setVisible(false);
        }

        try {

            updateCatalog();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void logout() {
        main.userLogout();
    }


    public void noteaza() {

        if(inEditare != null) {
            eroare.setText("");
            inEditare.setMat(clasaDeschisa.getMat());
            inEditare.setSolicitant(profesorLogat);
            inEditare.setClasa(clasaDeschisa);
            inEditare.setCpm(clasaDeschisa.getCpm());
            new Client.ModalWindow("/Client/view/Noteaza.fxml", inEditare);

        }else{
            eroare.setText("Selectati un elev din catalog!");
        }
        updateCatalog();

    }

    public void absenta(){

        if(inEditare != null) {
            eroare.setText("");
            inEditare.setMat(clasaDeschisa.getMat());
            inEditare.setSolicitant(profesorLogat);
            inEditare.setClasa(clasaDeschisa);
            inEditare.setCpm(clasaDeschisa.getCpm());
            new Client.ModalWindow("/Client/view/Absenta.fxml", inEditare);

        }else{
            eroare.setText("Selectati un elev din catalog!");
        }
    }

    public void motiveazaAbsente(){
        System.out.println("Motivare");
        if(inEditare != null) {
            eroare.setText("");
            inEditare.setMat(clasaDeschisa.getMat());
            inEditare.setSolicitant(profesorLogat);
            inEditare.setClasa(clasaDeschisa);
            inEditare.setCpm(clasaDeschisa.getCpm());
            new Client.ModalWindow("/Client/view/Motiveaza.fxml", inEditare);

        }else{
            eroare.setText("Selectati un elev din catalog!");
        }
    }

    public void updateCatalog(){
        nume.setMinWidth(100);
        prenume.setMinWidth(100);

        nume.setCellValueFactory(new PropertyValueFactory<Elev, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Elev, String>("prenume"));
        note.setCellValueFactory(new PropertyValueFactory<Elev, String>("note"));

        Elev e = new Elev();
        e.setClasa(clasaDeschisa);
        e.setSolicitant(profesorLogat);
        e.setActiune(Elev.Actiuni.read);

        Platform.runLater(() -> loading.setVisible(true));
        Task<Void> load= new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(100);

                    data = FXCollections.observableArrayList((List<Elev>) new Send().send(e));
                    for(Elev i:data){

                        Nota deTrimis = new Nota(i);
                        deTrimis.setActiune(Nota.Actiuni.read);
                        List<Nota> not = (List<Nota>) new Send().send(deTrimis);
                        i.setNote(not.toString());
                    }

                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        load.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                elevi.setItems(data);
                Platform.runLater(() -> loading.setVisible(false));
            }
        });

        new Thread(load).start();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
