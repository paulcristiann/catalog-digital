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

        //System.out.println(requestType);

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
                    System.out.println(requestType);
                    switch (requestType) {

                        case "class model.Profesor":
                            return new ProfesoriController().exec((Profesor) request);
                        case "class model.Materie":
                            return new MaterieController().exec((Materie) request);
                        case "class model.adminClasa":
                            return new ClaseController().exec((adminClasa) request);
                        case "class model.Parinte":
                            return new ParintiController().exec((Parinte)request);
                        case "class model.Semestru":
                            return new SemestruController().exec((Semestru)request);
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
                        case "class model.Elev":
                            return new EleviController().exec((Elev) request);
                        case "class model.Nota":
                            return new NotaController().exec((Nota) request);
                        case "class model.Semestru":
                            return new SemestruController().exec((Semestru) request);
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