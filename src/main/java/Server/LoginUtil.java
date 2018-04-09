package Server;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginUtil {
    public static String parola(String parola) {
        return new BCryptPasswordEncoder().encode(parola);
    }
}
