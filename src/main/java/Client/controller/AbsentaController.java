package Client.controller;

import Client.Send;
import Client.interfaces.ModalWindow;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Elev;
import model.Nota;

public class AbsentaController implements ModalWindow {

    public Label numeElev;

    private Elev elev;

    public void puneAbsenta(){
        Nota n = new Nota(-1);
        n.setActiune(Nota.Actiuni.create);
        n.setElev(elev);
        n.setProfesor(elev.getSolicitant());
        n.setMaterie(elev.getMat());
        n.setClasa(elev.getClasa());
        n.setCpm(elev.getCpm());
        n.setSemestru(SemestruController.getSemestrulCurent());
        try {
            n = (Nota) new Send().send(n);
        } catch (Exception e) {
            System.out.println(e);
        }
        new Client.ModalWindow("/Client/view/PopUp.fxml", n);
        close();
    }

    @Override
    public void setData(Object o) {

        Elev e = (Elev) o;
        numeElev.setText(e.getNume() + " " + e.getPrenume());
        elev = e;
    }

    private void close() {
        Stage stage = (Stage) numeElev.getScene().getWindow();
        stage.close();
    }

}
