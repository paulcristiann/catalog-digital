package Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class db {

    public static Connection getCon() {
        Connection con = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://tihenea.tk/catalog?" +
                    "user=proiectmds&password=proiectmds123&verifyServerCertificate=true&useSSL=true");

        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
