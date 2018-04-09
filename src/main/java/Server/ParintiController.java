package Server;

import model.Parinte;
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

public class ParintiController {

    public Object exec(Parinte p) {

        Connection con = getCon();
        QueryRunner run = new QueryRunner();

        try {
            switch (p.getProcedura()) {
                case citeste:
                    List<Parinte> result = run.query(con,
                            "SELECT id,nume,prenume,email from parinti",
                            new BeanListHandler<Parinte>(Parinte.class));

                    return result;

                case stergere:
                    if (run.update(con, "DELETE FROM parinti where id = ?", p.getId()) != 1)
                        p.setEroare("A aparut o eroare");
                    break;
                case adaugare:
                    p.setEroare(check(p));
                    if (p.getEroare().equals("")) {
                        if (run.update(con, "INSERT INTO parinti (nume,prenume,email,parola,telefon) VALUES (?,?,?,?,?)",
                                p.getNume(), p.getPrenume(), p.getEmail(), parola(p.getParola()), p.getTelefon()) != 1)
                            p.setEroare("A aparut o eroare");
                        else
                            p = run.query(con, "SELECT id,nume,prenume,email,telefon FROM parinti WHERE email= ? ",
                                    new BeanHandler<Parinte>(Parinte.class),
                                    p.getEmail());
                    }

                    break;
                case modificare:
                    p.setEroare(check(p));
                    if (p.getEroare().equals("")) {
                        if (run.update(con, "UPDATE parinti SET nume= ?,prenume = ?,email =  ?,parola= ?, telefon= ? WHERE id = ?",
                                p.getNume(), p.getPrenume(), p.getEmail(), parola(p.getParola()), p.getTelefon(), p.getId()) != 1)
                            p.setEroare("A aparut o eroare");
                        break;

                    }
            }
            DbUtils.close(con);
        } catch (SQLException e) {
            System.out.println(e);
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                p.setEroare("Email-ul trebuie sa fie unic");
                //foreign key constraint
            else if (e.getErrorCode() == 1451) {
                p.setEroare("Pentru a stege un parinte trebuie sa stergeti intai copii ");
                p.setCodEroare(1451);
            } else
                p.setEroare("MySQL err");
        }
        return p;
    }

    private String check(Parinte p) {
        if (p.getNume().length() < 3 || !Pattern.matches("[A-Za-z]+", p.getNume()))
            return "Numele trebuie sa contina minim 3 litere";
        else if (p.getPrenume().length() < 3 || !Pattern.matches("[A-Za-z]+", p.getNume()))
            return "Prenumele trebuie sa contina minim 3 litere";
        else if (p.getParola().length() < 7)
            return "Parola trebuie sa contina minim 7 litere";
        else if (!Pattern.matches("[0-9]+", p.getTelefon()))
            return "Numarul de telefon trebuie sa contina numai cifre";
        else
            return "";
    }
}

