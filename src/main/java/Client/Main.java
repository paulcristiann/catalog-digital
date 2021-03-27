package Client;

import Client.aspects.Loggable;
import Client.controller.AdministrareController;
import Client.controller.AlegeClasaController;
import Client.controller.CatalogController;
import Client.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Clasa;
import model.Login;

import java.io.InputStream;


public class Main extends Application {

    private static Stage stage;
    private static Login loggedUser=null;
    private Scene scene = null;

    public static Stage getStage() {
        return stage;
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

    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Loggable
    public boolean userLogging(String user, String password, Boolean administrare) {
        Login l = new Login(user, password);
        l.setAdministrare(administrare);
        Send s = new Send();
        Object o = s.send(l);
        loggedUser = (model.Login) o;

        if (loggedUser.isLogged()) {
            System.out.println(loggedUser.getToken());
            if (loggedUser.getAdministrare()) {
                openAdministrare();
            } else {
                openSelector(loggedUser);
            }
            return true;
        } else {
            loggedUser = null;
            return false;
        }
    }

    public void userLogout() {
        loggedUser = null;
        openLogin();
    }

    private void openAdministrare() {

        try {
            AdministrareController admin = (AdministrareController) replaceSceneContent("/Client/view/Administrare.fxml");
            admin.setApp(this);
            stage.setFullScreen(true);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void openCatalog(Login user, Clasa clasaSelectata) {

        try {
            CatalogController catalog = (CatalogController) replaceSceneContent("/Client/view/Catalog.fxml");
            catalog.setProfesorLogat(user);
            catalog.setClasaDeschisa(clasaSelectata);
            catalog.setApp(this);
            stage.setFullScreen(true);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    private void openSelector(Login user) {


        try{

            AlegeClasaController selector = (AlegeClasaController) replaceSceneContent("/Client/view/AlegeClasa.fxml");
            selector.setUser(user);
            selector.setApp(this);

        }catch (Exception ex) {

            System.out.println(ex);

        }


    }

    private void openLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("/Client/view/Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     *
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        scene = new Scene(page);

        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    public static Login getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Login loggedUser) {
        Main.loggedUser = loggedUser;
    }

}