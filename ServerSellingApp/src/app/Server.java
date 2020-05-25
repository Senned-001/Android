package app;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
    private static volatile Server instance = null;
    private final int SERVER_PORT = 6789;
    private ServerSocket serverSocket = null;

    private Server() {
    }

    public static Server getServer() {
        if (instance == null) {
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server();
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {

        try {
            /* Create serverSocket */
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Start server on port: " + SERVER_PORT);
            Main.changeStatus("Status: Connected");

            while(true) {
                UserConnection user = null;
                try {
                    /* wait new connection  */
                    user = new UserConnection(serverSocket.accept());
                    System.out.println("Get client connection");
                    /* for every connection - create new thread */
                    Thread t = new Thread(user);
                    t.start();
                } catch (Exception e) {
                    System.out.println("Connection error: "+e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Cant start server on port "+SERVER_PORT+":"+e.getMessage());
        } finally {
            /* close serverSocket */
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
