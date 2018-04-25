package Server;

import model.adminClasa;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static Server.db.getCon;

public class ClaseController {


    public Object exec(adminClasa c) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();
        try {
            switch (c.getActiune()) {


                case read:
                    List<adminClasa> result = run.query(con,
                            "SELECT clase.id,clase.nume,clase.id_diriginte, " +
                                        "CONCAT(profesori.nume,\" \",profesori.prenume)   diriginte " +
                                    "from clase join profesori on (clase.id_diriginte=profesori.id)",
                            new BeanListHandler<>(adminClasa.class));

                    return result;
                case create:
                    if (c.getNume().length() < 1)
                        c.setEroare("Adaugati numele clasei");
                    else if (c.getId_diriginte() == -1)
                        c.setEroare("Trebuie sa alegeti un diriginte pentru clasa");
                    else if (run.update(con, "INSERT INTO clase (nume,id_diriginte) VALUES (?,?)",
                            c.getNume(), c.getId_diriginte()) != 1)
                        c.setEroare("A aparut o eroare");
                    else
                        c = run.query(con, "SELECT clase.id,clase.nume,clase.id_diriginte, " +
                                        " CONCAT(profesori.nume,\" \",profesori.prenume) diriginte " +
                                        " from clase join profesori on (clase.nume =? AND clase.id_diriginte=profesori.id)",
                                new BeanHandler<adminClasa>(adminClasa.class),
                                c.getNume());

                    break;
                case update:
                    if (c.getNume().length() < 1)
                        c.setEroare("Adaugati numele clasei");
                    else if (c.getId_diriginte() == -1)
                        c.setEroare("Trebuie sa alegeti un diriginte pentru clasa");

                    else if (run.update(con, "UPDATE clase set nume=?, id_diriginte=? where id=?",
                            c.getNume(), c.getId_diriginte(), c.getId()) != 1)
                        c.setEroare("A aparut o eroare");
                    else
                        c = run.query(con, "SELECT clase.id,clase.nume,clase.id_diriginte, " +
                                        " CONCAT(profesori.nume,\" \",profesori.prenume) diriginte " +
                                        " from clase join profesori on (clase.id =? AND clase.id_diriginte=profesori.id)",
                                new BeanHandler<adminClasa>(adminClasa.class),
                                c.getId());


                    break;
                case delete:
                    if (run.update(con, "DELETE FROM clase where id = ?", c.getId()) != 1)
                        c.setEroare("A aparut o eroare");

                    break;
            }
            DbUtils.close(con);
        } catch (SQLException e) {
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                c.setEroare("Doua clase nu pot avea acelasi diriginte");
            else
                c.setEroare("MySQL err");
            System.out.println(e);
        }
        return c;
    }
}

