package Server;

import model.Elev;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class EleviController {

    public Object exec(Elev e) {

        Connection con = getCon();
        try {


            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (e.getActiune()) {
                case read:
                    sql = "SELECT id,nume,prenume from elevi where id_clasa=  " + Integer.toString(e.getClasa().getId());

                    List<Elev> result = new ArrayList<>();

                    rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        Elev el = new Elev();
                        el.setId(rs.getInt("id"));
                        el.setNume(rs.getString("nume"));
                        el.setPrenume(rs.getString("prenume"));
                        result.add(el);

                    }
                    rs.close();

                    return result;


            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return e;
    }
}
