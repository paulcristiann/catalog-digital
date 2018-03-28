package Server;

import model.Profesor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

public class ProfesoriController {

    public Object exec(Profesor p) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();

        try {
            switch (p.getActiune()) {
                case read:
                    List<Profesor> result = run.query(con,
                            "SELECT id,nume,prenume,email from profesori",
                            new BeanListHandler<Profesor>(Profesor.class));

                    return result;

                case delete:
                    if (run.update(con,"DELETE FROM profesori where id = ?", p.getId()) != 1)
                        p.setEroare("A aparut o eroare");

                    break;
                case create:
                    if (run.update(con,"INSERT INTO profesori (nume,prenume,email,parola) VALUES (?,?,?,?)",
                            p.getNume(), p.getPrenume(), p.getEmail(), parola(p.getParola())) != 1)
                        p.setEroare("A aparut o eroare");
                    else
                        p = run.query(con, "SELECT id,nume,prenume,email FROM profesori WHERE email= ? ",
                                new BeanHandler<Profesor>(Profesor.class),
                                p.getEmail());

                    break;
                case update:
                    if (run.update(con,"UPDATE profesori SET nume= ?,prenume = ?,email =  ?,parola= ? WHERE id = ?",
                            p.getNume(), p.getPrenume(), p.getEmail(), parola(p.getParola()), p.getId()) != 1)
                        p.setEroare("A aparut o eroare");

                    break;
            }
            DbUtils.close(con);
        } catch (SQLException e) {
            e.printStackTrace();
            p.setEroare("MySQL err");
        }
        return p;
    }
}
