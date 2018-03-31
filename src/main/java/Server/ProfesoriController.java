package Server;

import model.Profesor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

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
                    if (run.update(con, "DELETE FROM profesori where id = ?", p.getId()) != 1)
                        p.setEroare("A aparut o eroare");

                    break;
                case create:
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
            }
            DbUtils.close(con);
        } catch (SQLException e) {
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                p.setEroare("Email-ul trebuie sa fie unic");
            else
                p.setEroare("MySQL err");
        }
        return p;
    }

    private String check(Profesor p) {
        if (p.getNume().length() < 3 || !Pattern.matches("[A-Za-z]+", p.getNume()))
            return "Numele trebuie sa contina minim 3 litere";
        else if (p.getPrenume().length() < 3 || !Pattern.matches("[A-Za-z]+", p.getNume()))
            return "Prenumele trebuie sa contina minim 3 litere";
        else if (p.getParola().length() < 7)
            return "Parola trebuie sa contina minim 7 litere";
//        else if (/***verificare email */)
//            return "Email-ul trebuie sa fie valid";
        else
            return "";
    }
}
