package Server;

import model.Parinte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

public class ParintiController {

    public Object exec(Parinte p) {

        Connection con = getCon();


        try {
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (p.getProcedura()) {
                case citeste:
                    List<Parinte> result = new ArrayList<>();
                    sql = "SELECT id,nume,prenume,email from parinti";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        Parinte pr = new Parinte();
                        pr.setId(rs.getInt("id"));
                        pr.setNume(rs.getString("nume"));
                        pr.setPrenume(rs.getString("prenume"));
                        pr.setEmail(rs.getString("email"));
                        result.add(pr);
                    }
                    return result;

                case stergere:
                    sql = "DELETE FROM parinti where id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, p.getId());
                    if (1 != ps.executeUpdate())
                        p.setEroare("A aparut o eroare");
                    break;
                case adaugare:
                    p.setEroare(check(p));
                    if (p.getEroare().equals("")) {

                        sql = "INSERT INTO parinti (nume,prenume,email,parola,telefon) VALUES (?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, p.getNume());
                        ps.setString(2, p.getPrenume());
                        ps.setString(3, p.getEmail());
                        ps.setString(4, parola(p.getParola()));
                        ps.setString(5, p.getTelefon());
                        if (1 != ps.executeUpdate())
                            p.setEroare("A aparut o eroare");
                        else {
                            sql = "SELECT id,nume,prenume,email,telefon FROM parinti WHERE email= ? ";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, p.getEmail());
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                p = new Parinte();
                                p.setId(rs.getInt("id"));
                                p.setNume(rs.getString("nume"));
                                p.setPrenume(rs.getString("prenume"));
                                p.setEmail(rs.getString("email"));
                                p.setTelefon(rs.getString("telefon"));

                            }
                        }

                    }

                    break;
                case modificare:
                    p.setEroare(check(p));
                    if (p.getEroare().equals("")) {
                        sql = "UPDATE parinti SET nume= ?,prenume = ?,email =  ?,parola= ?, telefon= ? WHERE id = ?";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, p.getNume());
                        ps.setString(2, p.getPrenume());
                        ps.setString(3, p.getEmail());
                        ps.setString(4, parola(p.getParola()));
                        ps.setString(5, p.getTelefon());
                        ps.setInt(6, p.getId());

                        if (1 != ps.executeUpdate())
                            p.setEroare("A aparut o eroare");
                        break;

                    }
            }

        } catch (SQLException e) {
            System.out.println(e);
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                p.setEroare("Email-ul trebuie sa fie unic");
                //foreign key constraint
            else if (e.getErrorCode() == 1451) {
                p.setEroare("Pentru a stege un parinte trebuie sa stergeti intai copii ");
                p.setCodEroare(1451);
            } else
                p.setEroare("MySQL err");
        }
        return p;
    }

    private String check(Parinte p) {
        if (p.getNume().length() < 3 || !Pattern.matches("[A-Za-z]+", p.getNume()))
            return "Numele trebuie sa contina minim 3 litere";
        else if (p.getPrenume().length() < 3 || !Pattern.matches("[A-Za-z]+", p.getNume()))
            return "Prenumele trebuie sa contina minim 3 litere";
        else if (p.getParola().length() < 7)
            return "Parola trebuie sa contina minim 7 litere";
        else if (!Pattern.matches("[0-9]+", p.getTelefon()))
            return "Numarul de telefon trebuie sa contina numai cifre";
        else
            return "";
    }
}

