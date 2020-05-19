package app;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class UserConnection implements Runnable {
    private Socket clientSocket = null;
    private InputStream inputStream = null;

    public UserConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        /* получаем входной поток */
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Cant get input stream");
        }
        /* создаем буфер для данных */
        byte[] buffer = new byte[1024*4];
        while(true) {
            try {
                /*
                 * получаем очередную порцию данных
                 * в переменной count хранится реальное количество байт, которое получили                  */
                int count = inputStream.read(buffer,0,buffer.length);
                /* проверяем, какое количество байт к нам прийшло */
                if (count > 0) {
                    String userMessage = new String(buffer,0,count);
                    System.out.println(userMessage);
                    Main.displayUserMessage(userMessage);
                } else
                    /* если мы получили -1, значит прервался наш поток с данными  */
                    if (count == -1 ) {
                        System.out.println("close socket");
                        clientSocket.close();
                        break;
                    }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
