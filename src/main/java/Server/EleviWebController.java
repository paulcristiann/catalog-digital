package Server;

import javafx.util.Pair;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

@Controller
public class EleviWebController {

    @GetMapping("/elevi")
    public String parinti(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Connection con = getCon();
        QueryRunner run = new QueryRunner();

        try {
            Elev elev = new Elev();
            String sql = "SELECT id,nume,prenume from elevi WHERE  email=? LIMIT 1";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, authentication.getName());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                elev.setId(rs.getInt("id"));

            }


            sql = "SELECT n.note,materii.nume FROM (\n" +
                    "    SELECT note.id_clasa_profesor_materie id , GROUP_CONCAT(\n" +
                    "        CONCAT( \n" +
                    "            IF(note.nota=-1,\"Absenta nemotivata\", \n" +
                    "               IF(note.nota=0,\"Absenta motivata\",note.nota)),\" \",note.data) SEPARATOR '<br>') AS note\n" +
                    "    FROM `note` WHERE note.id_elev=? AND note.semestru=? GROUP BY note.id_clasa_profesor_materie) AS `n`\n" +
                    "    JOIN clasa_profesor_materie ON (n.id=clasa_profesor_materie.id)\n" +
                    "    JOIN materii ON (clasa_profesor_materie.id_materie=materii.id)";

           /** semestrul I **/
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, elev.getId());
            preparedStatement.setInt(2, 1);
            rs = preparedStatement.executeQuery();
            List<Pair<String, String>> sem1 = new ArrayList<Pair<String, String>>();
            while (rs.next())
                sem1.add(new Pair(rs.getString("nume"),
                        rs.getString("note")));

            elev.setSemestrul1(sem1);

            /** semestrul II **/
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, elev.getId());
            preparedStatement.setInt(2, 2);
            rs = preparedStatement.executeQuery();
            List<Pair<String, String>> sem2 = new ArrayList<Pair<String, String>>();
            while (rs.next())
                sem2.add(new Pair(rs.getString("nume"),
                        rs.getString("note")));

            elev.setSemestrul2(sem2);


            model.addAttribute("elev", elev);
        } catch (Exception e) {
            System.out.println(e);
        }


        return "elevi";
    }

    class Elev {
        public String nume;
        int id;

        List<Pair<String, String>> semestrul1;
        List<Pair<String, String>> semestrul2;

        public Elev() {

        }

        public List<Pair<String, String>> getSemestrul1() {
            return semestrul1;
        }

        public void setSemestrul1(List<Pair<String, String>> semestrul1) {
            this.semestrul1 = semestrul1;
        }

        public List<Pair<String, String>> getSemestrul2() {
            return semestrul2;
        }

        public void setSemestrul2(List<Pair<String, String>> semestrul2) {
            this.semestrul2 = semestrul2;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNume() {
            return nume;
        }

        public void setNume(String nume) {
            this.nume = nume;
        }
    }
}

