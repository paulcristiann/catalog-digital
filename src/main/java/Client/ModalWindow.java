package Client;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class ModalWindow {
    private FXMLLoader loader;
    private Stage dialog;

    public ModalWindow(String fxml,Object data) {
        dialog = new Stage();

        loader = new FXMLLoader();

        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scene scene = new Scene(page);

        dialog.setScene(scene);


        Client.interfaces.ModalWindow mw = (Client.interfaces.ModalWindow) loader.getController();
        mw.setData(data);

        dialog.sizeToScene();

        dialog.initOwner(Main.getStage());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
