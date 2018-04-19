package Server;

import model.Elevi;
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


public class EleviControllerAdmin {
    public Object exec(Elevi elev) {

        Connection con = getCon();
        QueryRunner run = new QueryRunner();
        try {
            switch (elev.getProcedura()) {
                case citeste:
                    List<Elevi> result = run.query(con,
                            "SELECT e.id,e.nume,e.prenume,e.email,c.id clasa ,c.nume numeclasa  , p.id parinte , p.nume numeparinte from elevi e join clase c on(e.id_clasa = c.id) join parinti p on(e.id_parinte= p.id)",
                            new BeanListHandler<Elevi>(Elevi.class));

                    return result;

                case stergere:
                    if (run.update(con, "DELETE FROM elevi where id = ?", elev.getId()) != 1)
                        elev.setEroare("A aparut o eroare");
                    break;
                case adaugare:
                    elev.setEroare(check(elev));
                    if (elev.getEroare().equals("")) {
                        if (run.update(con, "INSERT INTO elevi (nume,prenume,email,parola,id_clasa,id_parinte) VALUES (?,?,?,?,?,?)",
                                elev.getNume(), elev.getPrenume(), elev.getEmail(), parola(elev.getParola()), elev.getClasa(), elev.getParinte()) != 1)
                            elev.setEroare("A aparut o eroare");
                        else
                            elev = run.query(con, "SELECT id,nume,prenume,email,id_clasa,id_parinte FROM elevi WHERE email= ? ",
                                    new BeanHandler<Elevi>(Elevi.class),
                                    elev.getEmail());
                    }

                    break;

                case modificare:
                    elev.setEroare(check(elev));
                    if (elev.getEroare().equals("")) {
                        if (run.update(con, "UPDATE elevi SET nume= ?,prenume = ?,email =  ?,parola= ?, id_clasa= ? , id_parinte=? WHERE id = ?",
                                elev.getNume(), elev.getPrenume(), elev.getEmail(), parola(elev.getParola()), elev.getClasa(), elev.getId(), elev.getParinte()) != 1)
                            elev.setEroare("A aparut o eroare");
                        break;

                    }
            }
            DbUtils.close(con);
        } catch (SQLException e) {
            System.out.println(e);
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                elev.setEroare("Email-ul trebuie sa fie unic");
                //foreign key constraint
            else
                elev.setEroare("MySQL err");
        }
        return elev;
    }


    private String check(Elevi elev) {
        if (elev.getNume().length() < 3 || !Pattern.matches("[A-Za-z]+", elev.getNume()))
            return "Numele trebuie sa contina minim 3 litere";
        else if (elev.getPrenume().length() < 3 || !Pattern.matches("[A-Za-z]+", elev.getNume()))
            return "Prenumele trebuie sa contina minim 3 litere";
        else if (elev.getParola().length() < 7)
            return "Parola trebuie sa contina minim 7 litere";
        else
            return "";
    }

}