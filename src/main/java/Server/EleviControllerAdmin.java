package Server;

import model.Elevi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static Server.LoginUtil.parola;
import static Server.db.getCon;


public class EleviControllerAdmin {
    public Object exec(Elevi elev) {

        Connection con = getCon();

        try {
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (elev.getProcedura()) {
                case citeste:
                    List<Elevi> result = new ArrayList<>();
                    sql = "SELECT e.id,e.nume,e.prenume,e.email,c.id clasa ,c.nume numeclasa  , p.id parinte , p.nume numeparinte from elevi e join clase c on(e.id_clasa = c.id) join parinti p on(e.id_parinte= p.id)";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        Elevi el = new Elevi();
                        el.setId(rs.getInt("id"));
                        el.setClasa(rs.getInt("clasa"));
                        el.setParinte(rs.getInt("parinte"));
                        el.setNume(rs.getString("nume"));
                        el.setPrenume(rs.getString("prenume"));
                        el.setEmail(rs.getString("email"));
                        el.setNumeClasa(rs.getString("numeclasa"));
                        el.setNumeParinte(rs.getString("numeparinte"));
                        result.add(el);
                    }

                    return result;

                case stergere:
                    sql = "DELETE FROM elevi where id = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, elev.getId());
                    if (1 != ps.executeUpdate())
                        elev.setEroare("A aparut o eroare");
                    break;
                case adaugare:
                    elev.setEroare(check(elev));
                    if (elev.getEroare().equals("")) {

                        sql = "INSERT INTO elevi (nume,prenume,email,parola,id_clasa,id_parinte) VALUES (?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, elev.getNume());
                        ps.setString(2, elev.getPrenume());
                        ps.setString(3, elev.getEmail());
                        ps.setString(4, parola(elev.getParola()));
                        ps.setInt(5, elev.getClasa());
                        ps.setInt(6, elev.getParinte());
                        if (1 != ps.executeUpdate())
                            elev.setEroare("A aparut o eroare");
                        else {
                            sql = "SELECT id,nume,prenume,email,id_clasa,id_parinte FROM elevi WHERE email= ? ";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, elev.getEmail());
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                elev = new Elevi();
                                elev.setId(rs.getInt("id"));
                                elev.setClasa(rs.getInt("id_clasa"));
                                elev.setParinte(rs.getInt("id_parinte"));
                                elev.setNume(rs.getString("nume"));
                                elev.setPrenume(rs.getString("prenume"));
                                elev.setEmail(rs.getString("email"));
                            }
                        }
                    }

                    break;

                case modificare:
                    elev.setEroare(check(elev));
                    if (elev.getEroare().equals("")) {

                        sql = "UPDATE elevi SET nume= ?,prenume = ?,email =  ?,parola= ?, id_clasa= ? , id_parinte=? WHERE id = ?";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, elev.getNume());
                        ps.setString(2, elev.getPrenume());
                        ps.setString(3, elev.getEmail());
                        ps.setString(4, parola(elev.getParola()));
                        ps.setInt(5, elev.getClasa());
                        ps.setInt(6, elev.getParinte());
                        ps.setInt(7, elev.getId());
                        if (1 != ps.executeUpdate())
                            elev.setEroare("A aparut o eroare");
                    }
                    break;
            }
        } catch (SQLException e) {
            System.out.println(e);
            // Mysql error Duplicate entry
            if (e.getErrorCode() == 1062)
                elev.setEroare("Email-ul trebuie sa fie unic");
                //foreign key constraint
            else
                elev.setEroare("MySQL err");
        }
        return elev;
    }


    private String check(Elevi elev) {
        if (elev.getNume().length() < 3 || !Pattern.matches("[A-Za-z]+", elev.getNume()))
            return "Numele trebuie sa contina minim 3 litere";
        else if (elev.getPrenume().length() < 3 || !Pattern.matches("[A-Za-z]+", elev.getNume()))
            return "Prenumele trebuie sa contina minim 3 litere";
        else if (elev.getParola().length() < 7)
            return "Parola trebuie sa contina minim 7 litere";
        else
            return "";
    }

}