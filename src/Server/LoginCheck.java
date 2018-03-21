package Server;

import model.Login;

import java.sql.*;

/**
 *
 */
public class LoginCheck {
    private Login request;

    public LoginCheck(Login request) {
        this.request = request;
    }

    public void check() {
        int ok = 0;
        Connection con = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://tihenea.tk/catalog?" +
                    "user=proiectmds&password=proiectmds123&verifyServerCertificate=true&useSSL=true");

        } catch (Exception e) {
            System.out.println(e);
        }

        // TODO: random token
        request.setToken("token");

        String sql = "update " +
                (request.getAdministrare() ? "administrare" : "profesori")
                + " set token = ? where nume = ? AND parola=? ";
        System.out.println(sql);

        try {
            PreparedStatement query = con.prepareStatement(sql);
            query.setString(1, request.getToken());
            query.setString(2, request.getUser());
            query.setString(3, request.getPassword());
            ok = query.executeUpdate();
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
}
