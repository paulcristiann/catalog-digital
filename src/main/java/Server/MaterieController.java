package Server;

import model.Materie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class MaterieController {

    public Object exec(Materie m) {
        Connection con = getCon();

        try {
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (m.getActiune()) {

                case create:
                    if (m.getNume().length() < 3)
                        m.setEroare("Numele materiei trebuie sa contina minim 3 caractere");

                    sql = "INSERT INTO materii (nume) VALUES (?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, m.getNume());
                    if (1 != ps.executeUpdate())
                        m.setEroare("A aparut o eroare");
                    else {
                        sql = "SELECT id,nume FROM materii WHERE nume= ? ";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, m.getNume());
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            m = new Materie();
                            m.setId(rs.getInt("id"));
                            m.setNume(rs.getString("nume"));

                        }
                    }
                    break;
                case read:
                    List<Materie> result = new ArrayList<>();
                    sql = "SELECT nume,id from materii";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        Materie mt = new Materie();
                        mt.setId(rs.getInt("id"));
                        mt.setNume(rs.getString("nume"));
                        result.add(mt);
                    }

                    return result;

                case update:
                    if (m.getNume().length() < 3)
                        m.setEroare("Numele materiei trebuie sa contina minim 3 caractere");

                    sql = "UPDATE materii set nume=? where id=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, m.getNume());
                    ps.setInt(2, m.getId());

                    if (1 != ps.executeUpdate())
                        m.setEroare("A aparut o eroare");
                    break;
                case delete:
                    sql = "DELETE FROM materii where id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, m.getId());
                    if (1 != ps.executeUpdate())
                        m.setEroare("A aparut o eroare");
                    break;

            }
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

