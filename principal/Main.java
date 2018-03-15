package principal;

import com.oracle.tools.packager.Log;
import utilitare.Login;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {


        System.out.println("Asteptam o conexiune.....\n");

        try {

            ServerSocket ss = new ServerSocket(12345);

            Socket soc = ss.accept();
            ObjectOutputStream os = new ObjectOutputStream(soc.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(soc.getInputStream());

            Object request = is.readObject();

            String requestType = request.getClass().toString();

            switch(requestType) {


                case "class utilitare.Login":

                    System.out.println("Cerere de login primita!");
                    if(CheckUser.verificareUtilizator((Login) request)) //verificam daca exista userul cu parola respectiva
                        CheckUser.conectareUtilizator((Login) request); //conectam userul

                    break;

                default:

                    System.out.println("Cerere neidentificata!");

                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
