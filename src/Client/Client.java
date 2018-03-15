package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Login;

import java.io.InputStream;


public class Client extends Application {

    private Stage stage;
    private Login loggedUser;

    public static void main(String[] args) {
        Application.launch(Client.class);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setMinWidth(300);
            stage.setMinHeight(300);
            openLogin();
            primaryStage.show();
        } catch (Exception ex) {

        }
    }

    public boolean userLogging(String user, String password) {
        Login l = new Login(user, password);
        Send s = new Send();
        Object o = s.send(l);
        loggedUser = (model.Login) o;

        if (loggedUser.isLogged()) {
            System.out.println(loggedUser.getToken());
            openCatalog();
            return true;
        } else {
            loggedUser=null;
            return false;
        }
    }

    void userLogout() {
        loggedUser = null;
        openLogin();
    }

    private void openCatalog() {

        try {
            CatalogController catalog = (CatalogController) replaceSceneContent("Catalog.fxml");
            catalog.setApp(this);
            stage.setFullScreen(true);
        } catch (Exception ex) {
            System.out.println("boom");
        }
    }

    private void openLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            System.out.println("boom");
        }
    }

    /**
     *
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Client.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Client.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        Scene scene = new Scene(page);

        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
