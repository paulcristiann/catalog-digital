package Server;

import model.Login;
import model.Materie;
import model.Profesor;
import model.Profesori;

/**
 *
 */
public class Response {

    private Object request;

    public Response(Object request) {
        this.request = request;
    }

    public Object gerResponse() {

        String requestType = request.getClass().toString();

        switch (requestType) {

            case "class model.Login":
                Login l = (Login) request;
                LoginCheck lc = new LoginCheck(l);
                lc.check();
                return l;
            case "class model.Profesori":
                Profesori p = (Profesori) request;
                p.setProfesori(new ProfesoriController().getProfesori());
                return p;
            case "class model.Profesor":
                return new ProfesoriController().exec((Profesor) request);
            case "class model.Materie":
                return new MaterieController().exec((Materie)request);
            default:
                return "Cerere neidentificata";

        }
    }
}