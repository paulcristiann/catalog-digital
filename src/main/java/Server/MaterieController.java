package Server;

import model.Materie;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
                    if (m.getNume().length() < 3)
                        m.setEroare("Numele materiei trebuie sa contina minim 3 caractere");
                    else if (run.update(con, "INSERT INTO materii (nume) VALUES (?)",
                            m.getNume()) != 1)
                        m.setEroare("A aparut o eroare");
                    else
                        m = run.query(con, "SELECT id,nume FROM materii WHERE nume= ? ",
                                new BeanHandler<Materie>(Materie.class),
                                m.getNume());

                    break;
                case read:
                    List<Materie> result = run.query(con,
                            "SELECT nume,id from materii",
                            new BeanListHandler<Materie>(Materie.class));

                    return result;

                case update:
                    if (m.getNume().length() < 3)
                        m.setEroare("Numele materiei trebuie sa contina minim 3 caractere");
                    else if (run.update(con, "UPDATE materii set nume=? where id=?",
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
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                m.setEroare("Numele materiei trebuie sa fie unic");
            else
                m.setEroare("MySQL err");
        }
        return m;
    }
}

