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
                elev.setNume(rs.getString("nume") + " " + rs.getString("prenume"));

            }


            sql = "SELECT " +
                    "  n.note, materii.nume, medii.nota medie,materii.areteza, teze.nota teza," +
                    "n.suma_note suma_note, n.nr_note  FROM " +
                    "  ( " +
                    "  SELECT " +
                    "    note.id_clasa_profesor_materie id, " +
                    "    note.id_elev id_elev, " +
                    "    SUM(CASE WHEN note.nota > 0 THEN note.nota ELSE 0 END) AS suma_note, " +
                    "    SUM(CASE WHEN note.nota > 0 THEN 1 ELSE 0 END) AS nr_note, " +
                    "    note.semestru semestru, GROUP_CONCAT( " +
                    "      CONCAT( " +
                    "        IF( " +
                    "          note.nota = -1, " +
                    "          \"Absenta nemotivata\", " +
                    "          IF( " +
                    "            note.nota = 0, " +
                    "            \"Absenta motivata\", " +
                    "            note.nota " +
                    "          ) " +
                    "        ), " +
                    "        \" \", " +
                    "        note.data " +
                    "      ) SEPARATOR '<br>' " +
                    "    ) AS note " +
                    "  FROM " +
                    "    `note` " +
                    "  WHERE " +
                    "    note.id_elev = ? AND note.semestru = ? " +
                    "  GROUP BY " +
                    "    note.id_clasa_profesor_materie " +
                    ") AS `n` " +
                    "JOIN " +
                    "  clasa_profesor_materie ON( " +
                    "    n.id = clasa_profesor_materie.id " +
                    "  ) " +
                    "JOIN " +
                    "  materii ON( " +
                    "    clasa_profesor_materie.id_materie = materii.id " +
                    "  ) " +
                    "LEFT JOIN " +
                    "  medii ON( " +
                    "    medii.elevi_id = n.id_elev AND medii.semestru = n.semestru AND medii.clasa_profesor_materie_id=n.id " +
                    "  )" +
                    " LEFT JOIN " +
                    "  teze ON( " +
                    "    teze.elevi_id = n.id_elev AND teze.semestru = n.semestru AND teze.clasa_profesor_materie_id=n.id " +
                    "  )";
            /** semestrul I **/
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, elev.getId());
            preparedStatement.setInt(2, 1);
            rs = preparedStatement.executeQuery();
            List<Materie> sem1 = new ArrayList<Materie>();
            while (rs.next()) {
                int teza = (rs.getBoolean("areteza")) ? rs.getInt("teza") : -1;
                sem1.add(new Materie(
                        rs.getString("nume"),
                        rs.getString("note"),
                        teza,
                        rs.getDouble("medie"),
                        rs.getInt("suma_note"),
                        rs.getInt("nr_note")));
            }
            elev.setSemestrul1(sem1);

            /** semestrul II **/
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, elev.getId());
            preparedStatement.setInt(2, 2);
            rs = preparedStatement.executeQuery();
            List<Materie> sem2 = new ArrayList<Materie>();
            while (rs.next()) {
                int teza = (rs.getBoolean("areteza")) ? rs.getInt("teza") : -1;
                sem2.add(new Materie(
                        rs.getString("nume"),
                        rs.getString("note"),
                        teza,
                        rs.getDouble("medie"),
                        rs.getInt("suma_note"),
                        rs.getInt("nr_note")));
            }
            elev.setSemestrul2(sem2);


            model.addAttribute("elev", elev);
        } catch (Exception e) {
            System.out.println(e);
        }


        return "elevi";
    }

    class Materie {
        private String nume;
        private String note;
        private int teza;
        private double medie;
        private int sumaNote;
        private int nrNote;

        public Materie(String nume, String note, int teza, double medie, int sumaNote, int nrNote) {
            this.nume = nume;
            this.note = note;
            this.teza = teza;
            this.medie = medie;
            this.sumaNote = sumaNote;
            this.nrNote = nrNote;
        }

        public int getSumaNote() {
            return sumaNote;
        }

        public void setSumaNote(int sumaNote) {
            this.sumaNote = sumaNote;
        }

        public int getNrNote() {
            return nrNote;
        }

        public void setNrNote(int nrNote) {
            this.nrNote = nrNote;
        }

        public String getNume() {
            return nume;
        }

        public void setNume(String nume) {
            this.nume = nume;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getTeza() {
            return teza;
        }

        public String Teza() {
            if (teza == -1) return "-";
            else return "" + teza;
        }

        public void setTeza(int teza) {
            this.teza = teza;
        }

        public double getMedie() {
            return medie;
        }

        public void setMedie(double medie) {
            this.medie = medie;
        }
    }

    class Elev {
        public String nume;
        int id;

        List<Materie> semestrul1;
        List<Materie> semestrul2;

        public Elev() {

        }

        public List<Materie> getSemestrul1() {
            return semestrul1;
        }

        public void setSemestrul1(List<Materie> semestrul1) {
            this.semestrul1 = semestrul1;
        }

        public List<Materie> getSemestrul2() {
            return semestrul2;
        }

        public void setSemestrul2(List<Materie> semestrul2) {
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

