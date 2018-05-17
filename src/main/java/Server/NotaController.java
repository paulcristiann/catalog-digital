package Server;

import model.Nota;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.db.getCon;

public class NotaController {
    public Object exec(Nota n) {
        Connection con = getCon();

        try {
            QueryRunner run = new QueryRunner();
            Statement stmt = con.createStatement();
            String sql;
            PreparedStatement ps;
            ResultSet rs;
            switch (n.getActiune()) {

                case create:

                    if (run.update(con,  " INSERT INTO note (nota,id_elev,semestru,id_clasa_profesor_materie) VALUES (?,?,?,?)",
                            n.getValoare(), n.getElev().getId(),n.getSemestru(),n.getCpm()) != 1) {
                        System.out.println("A aparut o eroare la introducerea notei");
                        return new Nota(-100);
                    } else
                        return new Nota(100);

                case read:
                    List<Nota> note = new ArrayList<>();
                    PreparedStatement pstmt;
                    ResultSet rset;
                    pstmt = con.prepareStatement("SELECT nota from note where id_elev=?");
                    pstmt.setInt(1,n.getElev().getId());
                    rset = pstmt.executeQuery();
                    while(rset.next())
                    {
                        if(rset.getInt("nota") != 0 && rset.getInt("nota") != -1)
                        note.add(new Nota(rset.getInt("nota")));
                    }
                    return note;

                case update:
                    break;

                case delete:
                    break;

                case teza:
                    sql = "SELECT * FROM teze WHERE elevi_id = " + n.getElev().getId() + " AND semestru = " + n.getSemestru();
                    List<Object> teze = run.query(con, sql, new BeanListHandler<Object>(Object.class));
                    if(teze.isEmpty()) {
                        //putem adauga o teza

                        if (run.update(con, " INSERT INTO teze (nota,elevi_id,clasa_profesor_materie_id,semestru) VALUES (?,?,?,?)",
                                n.getValoare(), n.getElev().getId(), n.getCpm(), n.getSemestru()) != 1) {
                            System.out.println("A aparut o eroare la introducerea tezei");
                            return new Nota(-100);
                        } else
                            return new Nota(100);
                    }
                    else
                        return new Nota(-100);
                case readAbsente:
                    String query = "select * from note where (data BETWEEN (NOW() - INTERVAL 14 DAY) AND NOW()) AND nota = -1 AND " +
                            "id_elev = " + n.getElev().getId();
                    List<Nota> abs = new ArrayList();
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while(rs.next()){

                        Nota not = new Nota(rs.getString("data"),-1,rs.getInt("id"));
                        abs.add(not);
                        System.out.println(rs.getString("data"));
                    }
                    return abs;
                case motiveazaAbsente:
                    if (run.update(con,"UPDATE note SET nota=? WHERE id=?",
                            0,n.getId()) != 1){
                        return new Nota(130);
                    }
                    else {
                        return new Nota(131);
                    }
            }

        } catch (SQLException e) {

            System.out.println(e);

        }
        return n;
    }
}
