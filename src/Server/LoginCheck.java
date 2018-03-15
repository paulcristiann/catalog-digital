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
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@109.101.226.44:1433:BDA", "admin", "");


            PreparedStatement updateToken = null;
            // TODO: random token
            request.setToken("token");

            try {
                updateToken = con.prepareStatement("update users set token = ? where username = ? AND password=? ");
                updateToken.setString(1, request.getToken());
                updateToken.setString(2, request.getUser());
                updateToken.setString(3, request.getPassword());
                ok = updateToken.executeUpdate();
            } catch (SQLException e) {
                System.out.println("sql error");

            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }


        if (ok == 1) {
            request.setLogged(true);
        } else {
            request.setLogged(false);
        }

    }
}
