package Server;

import model.Clasa;
import model.Materie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Server.db.getCon;

public class ClasaController {

    public Object exec(Clasa m) {

        //Obtinem din BD clasele unui profesor

        int id_prof = -1;

        Connection con = getCon();

        PreparedStatement pstmt;
        ResultSet rs;

        ArrayList<Clasa> arr = new ArrayList<Clasa>();

        try{

            pstmt = con.prepareStatement("SELECT id from profesori where email=?");
            pstmt.setString(1,m.getSolicitant());
            rs = pstmt.executeQuery();

            rs.next();
            id_prof = rs.getInt("id");

        }catch(SQLException e){
            System.out.println(e);
        }

        try{

            pstmt = con.prepareStatement("select c.id, c.nume, c.id_diriginte, m.nume, m.id\n" +
                    "from clase as c, clasa_profesor_materie as cpm, materii as m\n" +
                    "where c.id = cpm.id_clasa AND cpm.id_profesor=? AND m.id = cpm.id_materie;");

            pstmt.setInt(1,id_prof);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                arr.add(new Clasa(rs.getInt("c.id"),rs.getString("c.nume"),rs.getInt("c.id_diriginte"),new Materie(rs.getString("m.nume"),rs.getInt("m.id"))));
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }
}
