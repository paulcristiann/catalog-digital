package Server;

import model.Arhiva;
import org.apache.commons.dbutils.QueryRunner;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class ArhivaController {


    String template = "";


    public Object exec() {


        Connection con = getCon();
        QueryRunner run = new QueryRunner();
        Arhiva arhiva = new Arhiva();
        try {
            List<Elev> elevi = new ArrayList<Elev>();
            String sql = "SELECT elevi.id,elevi.nume,elevi.prenume,clase.nume clasa from elevi " +
                    "   JOIN clase ON( elevi.id_clasa = clase.id  )";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet rs1 = preparedStatement.executeQuery();
            while (rs1.next()) {
                Elev elev = new Elev();
                elev.setId(rs1.getInt("id"));
                elev.setNume(rs1.getString("nume") + " " + rs1.getString("prenume") + " : " + rs1.getString("clasa"));


                sql = "SELECT  \n" +
                        "                     n.note,CONCAT( materii.nume,\"<br>\",profesori.nume,\" \",profesori.prenume) nume, medii.nota medie,materii.areteza, teze.nota teza, \n" +
                        "                   n.suma_note suma_note, n.nr_note  FROM  \n" +
                        "                     (  \n" +
                        "                     SELECT  \n" +
                        "                       note.id_clasa_profesor_materie id,  \n" +
                        "                       note.id_elev id_elev,  \n" +
                        "                       SUM(CASE WHEN note.nota > 0 THEN note.nota ELSE 0 END) AS suma_note,  \n" +
                        "                       SUM(CASE WHEN note.nota > 0 THEN 1 ELSE 0 END) AS nr_note,  \n" +
                        "                       note.semestru semestru, GROUP_CONCAT(  \n" +
                        "                         CONCAT(  \n" +
                        "                           IF(  \n" +
                        "                             note.nota = -1,  \n" +
                        "                             \"Absenta nemotivata\",  \n" +
                        "                             IF(  \n" +
                        "                               note.nota = 0,  \n" +
                        "                               \"Absenta motivata\",  \n" +
                        "                               note.nota  \n" +
                        "                             )  \n" +
                        "                           ),  \n" +
                        "                           \" \",  \n" +
                        "                           note.data  \n" +
                        "                         ) SEPARATOR '<br>'  \n" +
                        "                       ) AS note  \n" +
                        "                     FROM  \n" +
                        "                       `note`  \n" +
                        "                     WHERE  \n" +
                        "                       note.id_elev = ? AND note.semestru = ?  \n" +
                        "                     GROUP BY  \n" +
                        "                       note.id_clasa_profesor_materie  \n" +
                        "                   ) AS `n`  \n" +
                        "                   JOIN  \n" +
                        "                     clasa_profesor_materie ON(  \n" +
                        "                       n.id = clasa_profesor_materie.id  \n" +
                        "                     )  \n" +
                        "                   JOIN  \n" +
                        "                     materii ON(  \n" +
                        "                       clasa_profesor_materie.id_materie = materii.id  \n" +
                        "                     )  \n" +
                        "                   JOIN  \n" +
                        "                     clase ON(  \n" +
                        "                       clasa_profesor_materie.id_materie = clase.id  \n" +
                        "                     )\n" +
                        "                   JOIN  \n" +
                        "                    profesori ON(  \n" +
                        "                       clasa_profesor_materie.id_materie = profesori.id  \n" +
                        "                     )\n" +
                        "                   LEFT JOIN  \n" +
                        "                     medii ON(  \n" +
                        "                       medii.elevi_id = n.id_elev AND medii.semestru = n.semestru AND medii.clasa_profesor_materie_id=n.id  \n" +
                        "                     ) \n" +
                        "                    LEFT JOIN  \n" +
                        "                     teze ON(  \n" +
                        "                       teze.elevi_id = n.id_elev AND teze.semestru = n.semestru AND teze.clasa_profesor_materie_id=n.id  \n" +
                        "                     )";
                /** semestrul I **/
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, elev.getId());
                preparedStatement.setInt(2, 1);
                ResultSet rs = preparedStatement.executeQuery();
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
                elevi.add(elev);
            }


            try {
                template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/arhiva.html")), StandardCharsets.UTF_8);
                Context ctx = new Context();
                ctx.setVariable("elevi", elevi);
                SpringTemplateEngine templateEngine = new SpringTemplateEngine();
                arhiva.setArhiva(templateEngine.process(template, ctx));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return arhiva;
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

        public void setTeza(int teza) {
            this.teza = teza;
        }

        public String Teza() {
            if (teza == -1) return "-";
            else return "" + teza;
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
