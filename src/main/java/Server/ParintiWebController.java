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
public class ParintiWebController {

    @GetMapping("/parinti")
    public String parinti(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Connection con = getCon();
        QueryRunner run = new QueryRunner();

        try {
            List<Elev> elevi = new ArrayList<Elev>();
            String sql = "SELECT id,nume,prenume from elevi WHERE id_parinte=(SELECT id from parinti WHERE email=? LIMIT 1)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, authentication.getName());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Elev e = new Elev();
                e.setId(rs.getInt("id"));
                e.setNume(rs.getString("nume") + " " + rs.getString("prenume"));
                elevi.add(e);
            }
            for (Elev elev : elevi) {

                sql = "SELECT n.note,materii.nume FROM (\n" +
                        "    SELECT note.id_clasa_profesor_materie id , GROUP_CONCAT(\n" +
                        "        CONCAT( \n" +
                        "            IF(note.nota=-1,\"Absenta nemotivata\", \n" +
                        "               IF(note.nota=0,\"Absenta motivata\",note.nota)),\" \",note.data) SEPARATOR '<br>') AS note\n" +
                        "    FROM `note` WHERE note.id_elev=? GROUP BY note.id_clasa_profesor_materie) AS `n`\n" +
                        "    JOIN clasa_profesor_materie ON (n.id=clasa_profesor_materie.id)\n" +
                        "    JOIN materii ON (clasa_profesor_materie.id_materie=materii.id)";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, elev.getId());
                rs = preparedStatement.executeQuery();
                List<Pair<String, String>> materii = new ArrayList<Pair<String, String>>();
                while (rs.next()) {
                    materii.add(new Pair(rs.getString("nume"),
                            rs.getString("note")));

                }
                elev.setMaterii(materii);
                }

            model.addAttribute("elevi", elevi);
        } catch (Exception e) {
            System.out.println(e);
        }


        return "parinti";
    }

    class Elev {
        public String nume;
        int id;

        List<Pair<String, String>> materii;

        public Elev() {

        }

        public List<Pair<String, String>> getMaterii() {
            return materii;
        }

        public void setMaterii(List<Pair<String, String>> materii) {
            this.materii = materii;
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

