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
        /* give connection */
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Cant get input stream");
        }
        /* create buffer for data */
        byte[] buffer = new byte[1024*4];
        while(true) {
            try {
                /*       receive data                                   */
                int count = inputStream.read(buffer,0,buffer.length);
                /* if dataSize > 0 */
                if (count > 0) {
                    String userMessage = new String(buffer,0,count);
                    System.out.println(userMessage);
                    Main.displayUserMessage(userMessage);
                } else
                    /* if dataSize = -1,  then connection is close  */
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
