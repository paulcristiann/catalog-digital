package Server;

import model.Clasa;

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

            pstmt = con.prepareStatement("select c.nume\n" +
                    "from clase as c, clasa_profesor_materie\n" +
                    "where c.id = clasa_profesor_materie.id_clasa AND clasa_profesor_materie.id_profesor=?;");

            pstmt.setInt(1,id_prof);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                arr.add(new Clasa(rs.getString("nume")));
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }
}
