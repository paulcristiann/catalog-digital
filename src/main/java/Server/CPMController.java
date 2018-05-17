package Server;

import model.CPM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class CPMController {
    public Object exec(CPM cpm) {
        Connection con = getCon();

        cpm.setEroare("");
        try {

            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (cpm.getActiune()) {
                case read:
                    List<CPM> result = new ArrayList<>();
                    sql = "SELECT clasa_profesor_materie.id id, clase.id idClasa,clase.nume numeClasa, profesori.id idProfesor, CONCAT(profesori.nume,\" \",profesori.prenume) numeProfesor,\n" +
                            "materii.id idMaterie , materii.nume numeMaterie FROM clasa_profesor_materie \n" +
                            "JOIN clase ON(clasa_profesor_materie.id_clasa=clase.id)\n" +
                            "JOIN profesori ON(clasa_profesor_materie.id_profesor=profesori.id)\n" +
                            "JOIN materii ON(clasa_profesor_materie.id_materie=materii.id)";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        CPM cpm2 = new CPM();
                        cpm2.setId(rs.getInt("id"));
                        cpm2.setIdClasa(rs.getInt("idClasa"));
                        cpm2.setNumeClasa(rs.getString("numeClasa"));
                        cpm2.setIdProfesor(rs.getInt("idProfesor"));
                        cpm2.setNumeProfesor(rs.getString("numeProfesor"));
                        cpm2.setIdMaterie(rs.getInt("idMaterie"));
                        cpm2.setNumeMaterie(rs.getString("numeMaterie"));
                        result.add(cpm2);
                    }

                    return result;

                case delete:
                    sql = "DELETE FROM clasa_profesor_materie where id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, cpm.getId());
                    if (1 != ps.executeUpdate())
                        cpm.setEroare("A aparut o eroare");
                    break;
                case create:
                    sql = "INSERT INTO clasa_profesor_materie (id_clasa,id_profesor,id_materie  ) VALUES (?,?,?)";
                    sql = "INSERT INTO materii (nume) VALUES (?)";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, cpm.getIdClasa());
                    ps.setInt(2, cpm.getIdProfesor());
                    ps.setInt(3, cpm.getIdMaterie());
                    if (1 != ps.executeUpdate())
                        cpm.setEroare("A aparut o eroare");

                    break;
//
            }

        } catch (SQLException e) {
            System.out.println(e);
            //foreign key constraint
            if (e.getErrorCode() == 1451)
                cpm.setEroare("Nu se poate sterge.\n " +
                        "Exista date care depind de aceasta inregistrare");
            else
                cpm.setEroare("MySQL err");
        }
        return cpm;
    }
}
