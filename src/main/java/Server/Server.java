package Server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import model.*;

public class Server extends Thread {
    private ServerSocket socket;

    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting on  ... " + socket.getLocalPort());
                Socket server = socket.accept();

                ObjectOutputStream outStream = new ObjectOutputStream(server.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(server.getInputStream());
                Object x = inStream.readObject();
                //System.out.println(x.getClass().toString());
                Response r = new Response(x);
                outStream.writeObject(r.gerResponse());

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found");
            }
        }
    }



    public static void main(String args[]) {
        int port = 2678;
        try {
            Thread t = new Server(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
