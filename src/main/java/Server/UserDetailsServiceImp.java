package Server;

import Server.model.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.*;

import static Server.db.getCon;


public class UserDetailsServiceImp implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        Connection con = getCon();

        try {

            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;

            sql = "SELECT id,email,parola FROM parinti WHERE email= ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setParola(rs.getString("parola"));

            }

            if (user != null) {
                user.setUser("parinte");
            } else {
                sql = "SELECT id,email,parola FROM elevi WHERE email= ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setParola(rs.getString("parola"));

                }

                if (user != null) {
                    user.setUser("elev");
                } else {
                    throw new UsernameNotFoundException("cont inexistent.");
                }

            }
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