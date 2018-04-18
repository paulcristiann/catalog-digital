package Server;

import model.Preaviz;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static Server.db.getCon;

public class PreavizController {

    public Object exec(Preaviz p) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();

        try {
            switch (p.getActiune()) {
                case verificare:
                    List<Preaviz> result = run.query(con,
                            "SELECT absente.numar nrAbsente,elevi.nume,elevi.prenume,elevi.id FROM " +
                                    "(SELECT  note.id_elev, SUM(CASE WHEN note.nota =-1 THEN 1 ELSE 0 END) AS numar FROM note GROUP BY note.id_elev) AS absente JOIN elevi ON (absente.id_elev=elevi.id AND absente.numar > 39)",
                            new BeanListHandler<Preaviz>(Preaviz.class));

                    return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
}
