package Server;

import model.Materie;

import java.sql.*;
import java.util.ArrayList;

import static Server.db.getCon;

public class MaterieController {

    public Object exec(Materie m) {
        Connection con = getCon();
        String sql;
        switch (m.getActiune()) {

            case create:
                sql = "INSERT INTO materii (nume) VALUES (?)";
                try {
                    PreparedStatement query = con.prepareStatement(sql);
                    query.setString(1, m.getMaterie());

                    if (query.executeUpdate() != 1) {
                        m.setEroare("A aparut o eroare");
                    } else {

                        sql = "SELECT id from materii where nume=? ";

                        try {
                            query.close();
                            query = con.prepareStatement(sql);
                            query.setString(1, m.getMaterie());

                            ResultSet rs = query.executeQuery();
                            while (rs.next()) {
                                m.setId(rs.getInt("id"));
                            }

                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case read:

                ArrayList<Materie> arr = new ArrayList<>();
                sql = "SELECT id,nume from materii";

                try {

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    while (rs.next()) {
                        arr.add(new Materie(
                                rs.getString("nume"),
                                rs.getInt("id")));
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }

                return arr;

            case update:
                sql = "UPDATE materii set nume=? where id=?";
                try {
                    PreparedStatement query = con.prepareStatement(sql);
                    query.setString(1, m.getMaterie());
                    query.setInt(2, m.getId());

                    if (query.executeUpdate() != 1) {
                        m.setEroare("A aparut o eroare");
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            case delete:
                sql = "DELETE FROM materii where id = ?";
                try {
                    PreparedStatement query = con.prepareStatement(sql);
                    query.setInt(1, m.getId());

                    if (query.executeUpdate() != 1) {
                        m.setEroare("A aparut o eroare");
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
        }
        return m;
    }
}

