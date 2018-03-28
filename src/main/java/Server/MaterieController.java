package Server;

import model.Materie;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static Server.db.getCon;

public class MaterieController {

    public Object exec(Materie m) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();
        try {
            switch (m.getActiune()) {

                case create:
                    if (run.update(con,"INSERT INTO materii (nume) VALUES (?)",
                            m.getNume()) != 1)
                        m.setEroare("A aparut o eroare");

                    break;
                case read:
                    List<Materie> result = run.query(con,
                            "SELECT nume,id from materii",
                            new BeanListHandler<Materie>(Materie.class));

                    return result;

                case update:
                    if (run.update(con, "UPDATE materii set nume=? where id=?",
                            m.getNume(), m.getId()) != 1)
                        m.setEroare("A aparut o eroare");

                    break;
                case delete:
                    if (run.update(con, "DELETE FROM materii where id = ?", m.getId()) != 1)
                        m.setEroare("A aparut o eroare");

                    break;
            }
            DbUtils.close(con);
        } catch (SQLException e) {
            e.printStackTrace();
            m.setEroare("MySQL err");
        }
        return m;
    }
}

