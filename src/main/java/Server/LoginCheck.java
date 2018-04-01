package Server;

import model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void check() {
        int ok = 0;
        Connection con = getCon();

        // TODO: random token
        request.setToken("token");

        String sql = "update " +
                (request.getAdministrare() ? "administrare" : "profesori")
                + " set token = ? where nume = ? AND parola = ? ";

        try {
            PreparedStatement query = con.prepareStatement(sql);
            query.setString(1, request.getToken());
            query.setString(2, request.getUser());
            query.setString(3, parola(request.getPassword()));
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
