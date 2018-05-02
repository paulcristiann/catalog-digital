package Client.controller;


import Client.Main;
import Client.Send;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import model.Arhiva;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ArhivareController {
    @FXML
    public void arhiva() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(Main.getStage());
        file = new File(file.toString() + ".html");
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(((Arhiva) new Send().send(new Arhiva())).getArhiva());
                fileWriter.close();
            } catch (IOException ex) {

            }
        }
    }
}
