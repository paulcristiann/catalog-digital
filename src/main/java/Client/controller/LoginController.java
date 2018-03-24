package Client.controller;

import Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField user;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Label error;

    Boolean administrare = false;


    public void profesor(ActionEvent event) {
        administrare = false;
    }

    public void admin(ActionEvent event) {
        administrare = true;
    }

    private Main main;


    public void setApp(Main application) {
        this.main = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setText("");
    }

    public void login(ActionEvent event) {
        if (main != null) {
            if (!main.userLogging(user.getText(), password.getText(), administrare)) {
                error.setText("User/Password  invalid " + user.getText());
            }
        }
    }
}
