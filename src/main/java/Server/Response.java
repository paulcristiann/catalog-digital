package Server;

import model.Login;

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
                Login l =  (Login)request;
                LoginCheck lc = new LoginCheck(l);
                lc.check();
                return l;

            default:
                return "Cerere neidentificata";

        }
    }
}