package Server;

import model.Preaviz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class PreavizController {

    public Object exec(Preaviz p) {
        Connection con = getCon();

        try {
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (p.getActiune()) {
                case verificare:
                    List<Preaviz> result = new ArrayList<>();
                    sql = "SELECT absente.numar nrAbsente,elevi.nume,elevi.prenume,elevi.id FROM " +
                            "(SELECT  note.id_elev, SUM(CASE WHEN note.nota =-1 THEN 1 ELSE 0 END) AS numar FROM note GROUP BY note.id_elev) AS absente JOIN elevi ON (absente.id_elev=elevi.id AND absente.numar > 39)";
                    rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        Preaviz pr = new Preaviz();
                        pr.setId(rs.getInt("id"));
                        pr.setNume(rs.getString("nume"));
                        pr.setPrenume(rs.getString("prenume"));
                        pr.setNrAbsente(rs.getInt("nrAbsente"));
                        result.add(pr);
                    }

                    return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * @param p
     * @return o lista cu elevi care au mai mult e 10 absente pentru clasa specificata in p
     */
    public Object run(Preaviz p) {

        Connection con = getCon();


        try {
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            List<Preaviz> result = new ArrayList<>();
            sql = "SELECT absente.numar nrAbsente,elevi.nume,elevi.prenume,elevi.id FROM \n" +
                    "                            (SELECT  note.id_elev, SUM(CASE WHEN note.nota =-1 THEN 1 ELSE 0 END) AS numar FROM note GROUP BY note.id_elev) AS absente \n" +
                    "                            JOIN elevi ON (absente.id_elev=elevi.id)\n" +
                    "                            JOIN clase ON(elevi.id_clasa=clase.id AND clase.id=? AND absente.numar >= 10)";


            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getDiriginte());
            rs = ps.executeQuery();
            while (rs.next()) {
                Preaviz pr = new Preaviz();
                pr.setId(rs.getInt("id"));
                pr.setNume(rs.getString("nume"));
                pr.setPrenume(rs.getString("prenume"));
                pr.setNrAbsente(rs.getInt("nrAbsente"));
                result.add(pr);
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
}
