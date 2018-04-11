package Server;

import model.Nota;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

public class NotaController {
    public Object exec(Nota n) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();
        try {

            switch (n.getActiune()) {

                case create:

                    if (run.update(con,  " INSERT INTO note (nota,id_elev,semestru,id_clasa_profesor_materie) VALUES (?,?,?,?)",
                            n.getValoare(), n.getElev().getId(),n.getSemestru(),n.getCpm()) != 1) {
                        System.out.println("A aparut o eroare la introducerea notei");
                        return new Nota(-100);
                    } else
                        return new Nota(100);

                case read:
                    break;

                case update:
                    break;

                case delete:
                    break;

                case teza:
                    String sql = "SELECT * FROM teze WHERE elevi_id = " + n.getElev().getId() + " AND semestru = " + n.getSemestru();
                    List<Object> teze = run.query(con, sql, new BeanListHandler<Object>(Object.class));
                    if(teze.isEmpty()) {
                        //putem adauga o teza

                        if (run.update(con, " INSERT INTO teze (nota,elevi_id,clasa_profesor_materie_id,semestru) VALUES (?,?,?,?)",
                                n.getValoare(), n.getElev().getId(), n.getCpm(), n.getSemestru()) != 1) {
                            System.out.println("A aparut o eroare la introducerea tezei");
                            return new Nota(-100);
                        } else
                            return new Nota(100);
                    }
                    else
                        return new Nota(-100);
            }
            DbUtils.close(con);
        } catch (SQLException e) {

            System.out.println(e);

        }
        return n;
    }
}
