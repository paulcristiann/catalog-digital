package utilitare;

import java.io.Serializable;
import java.util.Date;

public class Login implements Serializable {

    public String username;
    public String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
