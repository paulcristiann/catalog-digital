package Server;

import Server.model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;

import static Server.db.getCon;


public class UserDetailsServiceImp implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        try {
            Connection con = getCon();
            QueryRunner run = new QueryRunner();

            user = run.query(con, "SELECT id,email,parola FROM parinti WHERE email= ? ",
                    new BeanHandler<User>(User.class),
                    username);
            user.setUser("parinte");
        } catch (SQLException e) {
            System.out.println(e);
        }


        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getParola());
            builder.roles(user.getUser());
        } else {
            throw new UsernameNotFoundException("cont inexistent.");
        }

        return builder.build();
    }
}