package app;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
    private static volatile Server instance = null;
    private final int SERVER_PORT = 6789;
    private ServerSocket serverSoket = null;

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
            /* Создаем серверный сокет, которые принимает соединения */
            serverSoket = new ServerSocket(SERVER_PORT);
            System.out.println("Start server on port: " + SERVER_PORT);
            Main.changeStatus("Connected");

            while(true) {
                UserConnection user = null;
                try {
                    /* ждем нового соединения  */
                    user = new UserConnection(serverSoket.accept());
                    System.out.println("Get client connection");
                    /* создается новый поток, в котором обрабатывается соединение */
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
            /* Закрываем соединение */
            if (serverSoket != null) {
                try {
                    serverSoket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
