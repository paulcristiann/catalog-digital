package Server;

import model.*;


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

        // login
        if (requestType.equals("class model.Login")) {
            Login lg = (Login) request;
            LoginCheck lc = new LoginCheck(lg);
            lc.check();
            return lg;
        } else if (model.Logged.class.isAssignableFrom(request.getClass())) {

            Logged l = (Logged) request;

            //administrator
            if (l.getAdministrare()) {
                if (LoginCheck.tokenIsValid(
                        LoginCheck.User.admin, l.getToken())) {

                    switch (requestType) {

                        case "class model.Profesor":
                            return new ProfesoriController().exec((Profesor) request);
                        case "class model.Materie":
                            return new MaterieController().exec((Materie) request);
                        case "class model.adminClasa":
                            return new ClaseController().exec((adminClasa) request);
                        default:
                            return "Cerere neidentificata";
                    }
                }
                //profesor
            } else {
                if (LoginCheck.tokenIsValid(
                        LoginCheck.User.profesor, l.getToken())) {

                    switch (requestType) {
                        case "class model.Clasa":
                            return new ClasaController().exec((Clasa) request);
                        default:
                            return "Cerere neidentificata";
                    }
                }
            }
        } else {
            System.err.println("Obiectul nu extinde Logged" + request.getClass());
        }
        return null;
    }
}