package Server;

import model.Clasa;
import model.Login;
import model.Materie;
import model.Profesor;

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

        System.out.println(requestType);

        switch (requestType) {

            case "class model.Login":
                Login l = (Login) request;
                LoginCheck lc = new LoginCheck(l);
                lc.check();
                return l;
            case "class model.Profesor":
                return new ProfesoriController().exec((Profesor) request);
            case "class model.Materie":
                return new MaterieController().exec((Materie)request);
            case "class model.Clasa":
                return new ClasaController().exec((Clasa) request);
            default:
                return "Cerere neidentificata";

        }
    }
}