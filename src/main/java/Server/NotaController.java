package Server;

import javafx.collections.FXCollections;
import model.Nota;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Server.LoginUtil.parola;
import static Server.db.getCon;

public class NotaController {
    public Object exec(Nota n) {
        Connection con = getCon();
        QueryRunner run = new QueryRunner();
        try {

            switch (n.getActiune()) {

                case create:

                    if (run.update(con,  " INSERT INTO note (nota,id_elev,semestru,id_clasa_profesor_materie) VALUES (?,?,?,?)",
                            n.getValoare(), n.getElev().getId(),n.getSemestru(),n.getCpm()) != 1) {
                        System.out.println("A aparut o eroare la introducerea notei");
                        return new Nota(-100);
                    } else
                        return new Nota(100);

                case read:
                    break;

                case update:
                    break;

                case delete:
                    break;

                case teza:
                    String sql = "SELECT * FROM teze WHERE elevi_id = " + n.getElev().getId() + " AND semestru = " + n.getSemestru();
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
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
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
            DbUtils.close(con);
        } catch (SQLException e) {

            System.out.println(e);

        }
        return n;
    }
}
