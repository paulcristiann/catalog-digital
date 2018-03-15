package Client;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogController implements Initializable {

    private Client client;


    public void setApp(Client application) {
        this.client = application;
    }

    public void logout() {
        client.userLogout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
