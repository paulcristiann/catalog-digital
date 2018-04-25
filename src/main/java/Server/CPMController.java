package Server;

import model.CPM;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static Server.db.getCon;

public class CPMController {
    public Object exec(CPM cpm) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();
        cpm.setEroare("");
        try {
            switch (cpm.getActiune()) {
                case read:
                    List<CPM> result = run.query(con,
                            "SELECT clasa_profesor_materie.id id, clase.id idClasa,clase.nume numeClasa, profesori.id idProfesor, CONCAT(profesori.nume,\" \",profesori.prenume) numeProfesor,\n" +
                                    "materii.id idMaterie , materii.nume numeMaterie FROM clasa_profesor_materie \n" +
                                    "JOIN clase ON(clasa_profesor_materie.id_clasa=clase.id)\n" +
                                    "JOIN profesori ON(clasa_profesor_materie.id_profesor=profesori.id)\n" +
                                    "JOIN materii ON(clasa_profesor_materie.id_materie=materii.id)",
                            new BeanListHandler<CPM>(CPM.class));

                    return result;

                case delete:
                    if (run.update(con, "DELETE FROM clasa_profesor_materie where id = ?", cpm.getId()) != 1)
                        cpm.setEroare("A aparut o eroare");
                    break;
                case create:
                    if (run.update(con, "INSERT INTO clasa_profesor_materie (id_clasa,id_profesor,id_materie  ) VALUES (?,?,?)",
                            cpm.getIdClasa(), cpm.getIdProfesor(), cpm.getIdMaterie()) != 1)
                        cpm.setEroare("A aparut o eroare");

                    break;
//
            }
            DbUtils.close(con);
        } catch (SQLException e) {
            System.out.println(e);
            //foreign key constraint
            if (e.getErrorCode() == 1451)
                cpm.setEroare("Nu se poate sterge.\n " +
                        "Exista date care depind de aceasta inregistrare");
            else
                cpm.setEroare("MySQL err");
        }
        return cpm;
    }
}
