package Client.controller;

import Client.Main;
import Client.aspects.Loggable;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministrareController implements Initializable {

    private Main main;
    @FXML
    private ProfesoriController includeProfesoriController;
    @FXML
    private ClaseController includeClaseController;
    @FXML
    private MateriiController includeMateriiController;
    @FXML
    private ParintiController includeParintiController;
    @FXML
    private EleviController includeEleviController;
    @FXML
    private SemestruController includeSemestruController;
    @FXML
    private PreavizController includePreavizController;
    @FXML
    private CPMController includeCPMController;

    @FXML
    private FlowPane loading;

    @FXML
    private TabPane meniu;


    public void setApp(Main application) {
        this.main = application;
    }

    public void logout() {
        main.userLogout();
    }

    @Override
    @Loggable
    public void initialize(URL location, ResourceBundle resources) {
        meniu.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab from, Tab to) {
                        new Thread(() -> {
                            Platform.runLater(() -> loading.setVisible(true));
                            switch (to.getText()) {
                                case "Preaviz":
                                    includePreavizController.start();
                                    break;
                                 case "Clase":
                                    includeClaseController.start();
                                    break;
                                case "Profesori":
                                    includeProfesoriController.start();
                                    break;
                                case "Materii":
                                    includeMateriiController.start();
                                    break;
                                case "Elevi":
                                    includeEleviController.start();
                                    break;
                                case "Parinti":
                                    includeParintiController.start();
                                    break;
                                case "Semestru":
                                    includeSemestruController.start();
                                case "CPM":
                                    includeCPMController.start();
                                    break;

                            }
                            Platform.runLater(() -> loading.setVisible(false));
                        }).start();
                    }
                }
        );
        new Thread(() -> {
            Platform.runLater(() -> loading.setVisible(true));
            includePreavizController.start();
            Platform.runLater(() -> loading.setVisible(false));
        }).start();

    }

}