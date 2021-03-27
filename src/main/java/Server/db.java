package Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class db {

    public static Connection getCon() {
        Connection con = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/catalog?" +
                    "user=root&password=1qaz!QAZ&verifyServerCertificate=false&useSSL=false");

        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
