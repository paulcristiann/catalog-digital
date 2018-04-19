package Server;

import model.ListaAbsente;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static Server.db.getCon;
import static model.ListaAbsente.Actiune.afisare;

public class ListaAbsenteController {
    public Object exec(ListaAbsente p) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();

        try {
                    List<ListaAbsente> result = run.query(con,
                            "SELECT  materii.nume materie, note.data FROM note  \n" +
                                    "JOIN clasa_profesor_materie ON(note.id_clasa_profesor_materie=clasa_profesor_materie.id)\n" +
                                    "JOIN materii ON( clasa_profesor_materie.id_materie=materii.id) WHERE note.id_elev=? AND note.nota=-1",
                            new BeanListHandler<ListaAbsente>(ListaAbsente.class),p.getId());
                    return result;
           } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }


}
