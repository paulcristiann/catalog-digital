package Client;


import java.io.*;
import java.net.Socket;

public class Send {
    public Object send(Object o) {

        int port = 3678;
        Object response = null;
        try {
            Socket client = new Socket("127.0.0.1", port);
            OutputStream os = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

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
