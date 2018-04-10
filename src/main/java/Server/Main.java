package Server;

import java.io.IOException;

public class Main {


    public static void main(String args[]) {
        int port = 2678;
        new Web().run();

        try {
            new Server(port).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
