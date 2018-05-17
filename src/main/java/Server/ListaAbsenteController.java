package Server;

import model.ListaAbsente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class ListaAbsenteController {
    public Object exec(ListaAbsente p) {
        Connection con = getCon();

        try {
            List<ListaAbsente> result = new ArrayList<>();


            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;

            sql = "SELECT  materii.nume materie, note.data FROM note  \n" +
                    "JOIN clasa_profesor_materie ON(note.id_clasa_profesor_materie=clasa_profesor_materie.id)\n" +
                    "JOIN materii ON( clasa_profesor_materie.id_materie=materii.id) WHERE note.id_elev=? AND note.nota=-1";

            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                ListaAbsente la = new ListaAbsente();
                la.setMaterie(rs.getString("materie"));
                la.setData(rs.getString("data"));
                result.add(la);
            }
            rs.close();

                    return result;
           } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }


}
