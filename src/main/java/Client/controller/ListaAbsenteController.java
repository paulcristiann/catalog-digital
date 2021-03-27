package Client.controller;

import Client.Send;
import Client.aspects.Loggable;
import Client.interfaces.ModalWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ListaAbsente;
import model.Preaviz;


import java.util.List;


public class ListaAbsenteController implements ModalWindow {

    private ObservableList<ListaAbsente> list;
    @FXML
    private TableView<ListaAbsente> table;
    @FXML
    private TableColumn<ListaAbsente, String> materie;
    @FXML
    private TableColumn<ListaAbsente, String> data;

    @Override
    @Loggable
    public void setData(Object o) {

        materie.setMinWidth(100);
        data.setMinWidth(100);
        materie.setCellValueFactory(new PropertyValueFactory<ListaAbsente, String>("materie"));
        data.setCellValueFactory(new PropertyValueFactory<ListaAbsente, String>("data"));
        Preaviz p = (Preaviz) o;
        ListaAbsente la = new ListaAbsente();
        la.setId(p.getId());

        list = FXCollections.observableArrayList((List<ListaAbsente>) new Send().send(la));
        table.setItems(list);



    }
}
