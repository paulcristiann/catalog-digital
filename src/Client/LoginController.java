package Client;
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

        private Client client;


        public void setApp(Client application){
            this.client = application;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            error.setText("");
        }

        public void login(ActionEvent event) {
            if (client != null){
                if (!client.userLogging(user.getText(), password.getText())){
                    error.setText("User/Password  invalid " + user.getText());
                }
            }
        }
    }
