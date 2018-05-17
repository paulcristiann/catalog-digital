package Server;

import model.adminClasa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class ClaseController {


    public Object exec(adminClasa c) {
        Connection con = getCon();

        try {
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (c.getActiune()) {


                case read:
                    List<adminClasa> result = new ArrayList<>();
                    sql = "SELECT clase.id,clase.nume,clase.id_diriginte, " +
                            "CONCAT(profesori.nume,\" \",profesori.prenume)   diriginte " +
                            "from clase join profesori on (clase.id_diriginte=profesori.id)";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        adminClasa ac = new adminClasa();
                        ac.setId(rs.getInt("id"));
                        ac.setId_diriginte(rs.getInt("id_diriginte"));
                        ac.setNume(rs.getString("nume"));
                        result.add(ac);
                    }

                    return result;
                case create:
                    if (c.getNume().length() < 1)
                        c.setEroare("Adaugati numele clasei");
                    else if (c.getId_diriginte() == -1)
                        c.setEroare("Trebuie sa alegeti un diriginte pentru clasa");
                    else {

                        sql = "INSERT INTO clase (nume,id_diriginte) VALUES (?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, c.getNume());
                        ps.setInt(2, c.getId_diriginte());
                        if (1 != ps.executeUpdate())
                            c.setEroare("A aparut o eroare");
                        else {
                            sql = "SELECT clase.id,clase.nume,clase.id_diriginte, " +
                                    " CONCAT(profesori.nume,\" \",profesori.prenume) diriginte " +
                                    " from clase join profesori on (clase.nume =? AND clase.id_diriginte=profesori.id)";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, c.getNume());
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                c = new adminClasa();
                                c.setId(rs.getInt("id"));
                                c.setId_diriginte(rs.getInt("id_diriginte"));
                                c.setNume(rs.getString("nume"));

                            }
                        }
                    }

                    break;
                case update:
                    if (c.getNume().length() < 1)
                        c.setEroare("Adaugati numele clasei");
                    else if (c.getId_diriginte() == -1)
                        c.setEroare("Trebuie sa alegeti un diriginte pentru clasa");

                    else {


                        sql = "UPDATE clase set nume=?, id_diriginte=? where id=?";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, c.getNume());
                        ps.setInt(2, c.getId_diriginte());
                        ps.setInt(3, c.getId());
                        if (1 != ps.executeUpdate())
                            c.setEroare("A aparut o eroare");
                        else {
                            sql = "SELECT clase.id,clase.nume,clase.id_diriginte, " +
                                    " CONCAT(profesori.nume,\" \",profesori.prenume) diriginte " +
                                    " from clase join profesori on (clase.id =? AND clase.id_diriginte=profesori.id)";
                            ps = con.prepareStatement(sql);
                            ps.setInt(1, c.getId());
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                c = new adminClasa();
                                c.setId(rs.getInt("id"));
                                c.setNume(rs.getString("nume"));
                                c.setId_diriginte(rs.getInt("id_diriginte"));
                            }
                        }
                    }

                    break;
                case delete:
                    sql = "DELETE FROM clase where id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, c.getId());
                    if (1 != ps.executeUpdate())
                        c.setEroare("A aparut o eroare");

                    break;
            }

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

