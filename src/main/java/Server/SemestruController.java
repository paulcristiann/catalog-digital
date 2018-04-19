package Server;

import model.Semestru;

import java.sql.*;

import static Server.db.getCon;

public class SemestruController {

    public Object exec(Semestru s) {
        if (s.isGet()) {
            s.setSemestru(getSemestrulCurent());

        } else {
            setSemestru(s.getSemestru());
        }
        return s;
    }

    /**
     * schimba semestrul in 2
     */
    public void setSemestru(int semestru) {
        try {
            Connection con = getCon();
            Statement statement = con.createStatement();
            statement.executeUpdate("TRUNCATE semestru");
            String sql = " INSERT INTO semestru VALUES(?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, semestru);
            preparedStatement.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * @return semestrulCurent
     */
    public static int getSemestrulCurent() {
        int semestru = 1;
        try {
            Connection con = getCon();
            String sql = "SELECT sem from semestru LIMIT 1";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                semestru = rs.getInt("sem");
                con.close();
            } else {
                System.err.println("Semestrul nu e definit");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return semestru;
    }
}
