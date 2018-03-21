package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    private Client client;


    public void setApp(Client application) {
        this.client = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setText("");
    }

    public void login(ActionEvent event) {
        if (client != null) {
            if (!client.userLogging(user.getText(), password.getText(), administrare)) {
                error.setText("User/Password  invalid " + user.getText());
            }
        }
    }
}
