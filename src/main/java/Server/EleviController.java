package Server;

import model.Elev;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

public class EleviController {

        public Object exec(Elev e) {

            Connection con = getCon();
            QueryRunner run = new QueryRunner();

            try {
                switch (e.getActiune()) {
                    case read:
                        String sql = "SELECT nume,prenume from elevi where id_clasa=" + Integer.toString(e.getClasa().getId());

                        List<Elev> result = run.query(con,
                                sql, new BeanListHandler<Elev>(Elev.class));

                        return result;

                    //case delete:
                        //if (run.update(con, "DELETE FROM profesori where id = ?", p.getId()) != 1)
                            //p.setEroare("A aparut o eroare");

                        //break;
                    /*case create:
                        p.setEroare(check(p));
                        if (p.getEroare().equals("")) {
                            if (run.update(con, "INSERT INTO profesori (nume,prenume,email,parola) VALUES (?,?,?,?)",
                                    p.getNume(), p.getPrenume(), p.getEmail(), parola(p.getParola())) != 1)
                                p.setEroare("A aparut o eroare");
                            else
                                p = run.query(con, "SELECT id,nume,prenume,email FROM profesori WHERE email= ? ",
                                        new BeanHandler<Profesor>(Profesor.class),
                                        p.getEmail());
                        }
                        break;
                    case update:
                        p.setEroare(check(p));
                        if (p.getEroare().equals("")) {
                            if (run.update(con, "UPDATE profesori SET nume= ?,prenume = ?,email =  ?,parola= ? WHERE id = ?",
                                    p.getNume(), p.getPrenume(), p.getEmail(), parola(p.getParola()), p.getId()) != 1)
                                p.setEroare("A aparut o eroare");
                        }
                        break;
                */
                }
                DbUtils.close(con);
            } catch (SQLException ex) {
                /*// Mysql error Duplicate entry
                if (e.getErrorCode() == 1062)
                    p.setEroare("Email-ul trebuie sa fie unic");
                else
                    p.setEroare("MySQL err");*/
            }
            return e;
        }
}
