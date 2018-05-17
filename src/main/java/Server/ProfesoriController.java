package Server;

import model.Profesor;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

public class ProfesoriController {

    public Object exec(Profesor p) {
        Connection con = getCon();

        try {
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (p.getActiune()) {
                case read:
                    List<Profesor> result = new ArrayList<>();
                    sql = "SELECT id,nume,prenume,email from profesori";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        Profesor pr = new Profesor();
                        pr.setId(rs.getInt("id"));
                        pr.setNume(rs.getString("nume"));
                        pr.setPrenume(rs.getString("prenume"));
                        pr.setEmail(rs.getString("email"));
                        result.add(pr);
                    }

                    return result;

                case delete:
                    sql = "DELETE FROM profesori where id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, p.getId());
                    if (1 != ps.executeUpdate())
                        p.setEroare("A aparut o eroare");
                    break;
                case create:
                    p.setEroare(check(p));
                    if (p.getEroare().equals("")) {
                        sql = "INSERT INTO profesori (nume,prenume,email,parola) VALUES (?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, p.getNume());
                        ps.setString(2, p.getPrenume());
                        ps.setString(3, p.getEmail());
                        ps.setString(4, parola(p.getParola()));
                        if (1 != ps.executeUpdate())
                            p.setEroare("A aparut o eroare");
                        else {
                            sql = "SELECT id,nume,prenume,email FROM profesori WHERE email= ? ";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, p.getEmail());
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                p = new Profesor();
                                p.setId(rs.getInt("id"));
                                p.setNume(rs.getString("nume"));
                                p.setPrenume(rs.getString("prenume"));
                                p.setEmail(rs.getString("email"));
                            }
                        }
                    }
                    break;
                case update:
                    p.setEroare(check(p));
                    if (p.getEroare().equals("")) {
                        sql = "UPDATE profesori SET nume= ?,prenume = ?,email =  ?,parola= ? WHERE id = ?";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, p.getNume());
                        ps.setString(2, p.getPrenume());
                        ps.setString(3, p.getEmail());
                        ps.setString(4, parola(p.getParola()));
                        ps.setInt(5, p.getId());
                        if (1 != ps.executeUpdate())
                            p.setEroare("A aparut o eroare");
                    }
                    break;
                case inlocuieste:
                    QueryRunner run = new QueryRunner();
                    run.update(con, "UPDATE clase SET id_diriginte= ? WHERE id_diriginte = ?",
                            p.getInlocuitor(), p.getId());

                    run.update(con, "UPDATE clasa_profesor_materie SET id_profesor= ? " +
                                    "WHERE id_profesor = ?",
                            p.getInlocuitor(), p.getId());

                    run.update(con, "UPDATE note SET id_profesor= ? " +
                                    "WHERE id_profesor = ?",
                            p.getInlocuitor(), p.getId());
                    break;
            }

        } catch (SQLException e) {
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                p.setEroare("Email-ul trebuie sa fie unic");
                //foreign key constraint
            else if (e.getErrorCode() == 1451) {
                p.setEroare("Profesorul nu poate fi sters.\n " +
                        "Inlocuitil cu alt profesor pentru a il sterge");
                p.setCodEroare(1451);
            } else
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
