package Client;


import model.Logged;

import java.io.*;
import java.net.Socket;

public class Send {

    public Object send(Object o){
    /** toate obiectele trimise catre server
     * trebuie sa extinda clasa model.Logged
     * pentru a putea verifica daca utilizatorul este autentificat,
     * mai putin model.Login
     * */
         //verificam daca extinde model.Logged
        if (model.Logged.class.isAssignableFrom(o.getClass())) {
            Logged l = (Logged) o;
            String token = (Main.getLoggedUser() != null)
                    ? Main.getLoggedUser().getToken() : null;
            l.setToken(token);
            Boolean admin = (Main.getLoggedUser() != null)
                    ? Main.getLoggedUser().getAdministrare() : null;
            l.setAdministrare(admin);

            ///verificam daca nu e modle.Login
        } else if (!model.Login.class.isAssignableFrom(o.getClass()))
            System.err.println("Obiectul nu extinde Logged" + o.getClass());


        int port = 2678;
        Object response = null;
        try {
            Socket client = new Socket("127.0.0.1", port);
            OutputStream os = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            //System.out.println(o.getClass().toString());

            oos.writeObject(o);

            InputStream is = client.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);

            response = in.readObject();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        return response;
    }
}
