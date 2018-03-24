package Server;

import model.Profesor;

import java.sql.*;
import java.util.ArrayList;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

public class ProfesoriController {


    public ArrayList<Profesor> getProfesori() {
        ArrayList<Profesor> arr = new ArrayList<Profesor>();
        int ok = 0;
        Connection con = getCon();


        String sql = "SELECT id,nume,prenume,email from profesori";

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                arr.add(new Profesor(
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("email"),
                        rs.getInt("id")));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return arr;
    }


    public Profesor exec(Profesor p) {
        Connection con = getCon();
        String sql;
        switch (p.getActiune()) {
            case delete:
                sql = "DELETE FROM profesori where id = ?";
                try {
                    PreparedStatement query = con.prepareStatement(sql);
                    query.setInt(1, p.getId());

                    if (query.executeUpdate() != 1) {
                        p.setEroare("A aparut o eroare");
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            case save:
                sql = "INSERT INTO profesori (nume,prenume,email,parola) VALUES (?,?,?,?)";
                try {
                    PreparedStatement query = con.prepareStatement(sql);
                    query.setString(1, p.getNume());
                    query.setString(2, p.getNume());
                    query.setString(3, p.getNume());
                    query.setString(4, parola(p.getParola()));

                    if (query.executeUpdate() != 1) {
                        p.setEroare("A aparut o eroare");
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }

                break;
            case update:
                break;
        }
        System.out.println(p.getNume());
        return p;
    }
}
