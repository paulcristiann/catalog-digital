package Server;

import model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

/**
 *
 */
public class LoginCheck {
    private Login request;

    public LoginCheck(Login request) {
        this.request = request;
    }

    public LoginCheck() {
    }

    public void check() {
        int ok = 0;
        Connection con = getCon();


        request.setToken(UUID.randomUUID().toString());

        String sql = "update " +
                (request.getAdministrare() ? "administrare" : "profesori")
                + " set token = ? , time=NOW() where email = ? AND parola = ? ";

        try {
            PreparedStatement query = con.prepareStatement(sql);
            query.setString(1, request.getToken());
            query.setString(2, request.getUser());
            query.setString(3, parola(request.getPassword()));
            ok = query.executeUpdate();
            if(ok == 1){

                //adaugam si id-ul
                PreparedStatement pstmt;
                ResultSet rs;
                pstmt = con.prepareStatement("SELECT id from " + (request.getAdministrare() ? "administrare" : "profesori") + " where email=?");
                pstmt.setString(1,request.getUser());
                rs = pstmt.executeQuery();
                rs.next();
                request.setId(rs.getInt("id"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        if (ok == 1) {
            request.setLogged(true);
        } else {
            request.setLogged(false);
        }
    }

    public enum User {admin, profesor}


    public static Boolean tokenIsValid(User u, String token) {
        Connection con = getCon();
        ResultSet rs = null;
        int ok = 0;
        try {
            if (u == User.admin) {
                String sql = "SELECT * FROM `administrare` WHERE DATE_ADD(time, INTERVAL 1 HOUR) > NOW() AND" +
                        " token=?";


                PreparedStatement query = con.prepareStatement(sql);
                query.setString(1, token);
                rs = query.executeQuery();


            } else {
                String sql = "SELECT * FROM `profesori` WHERE DATE_ADD(time, INTERVAL 1 HOUR) > NOW() AND" +
                        " token=?";


                PreparedStatement query = con.prepareStatement(sql);
                query.setString(1, token);
                rs = query.executeQuery();
            }

            if (rs.next()) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}


