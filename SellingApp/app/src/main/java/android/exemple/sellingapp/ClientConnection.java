package android.exemple.sellingapp;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private static final String LOG_TAG = "ClientConnection";
    private String mServerName = "192.168.1.11";
    private int mServerPort = 6789;
    private Socket mSocket = null;

    public ClientConnection() {
    }

    public void openConnection() throws Exception{
        closeConnection();
        try {
            mSocket = new Socket(mServerName,mServerPort);
        }
        catch (IOException e) {
            throw new Exception("Can't open socket: "+e.getMessage());
        }
    }

    public void sendData(byte[] data) throws Exception {
        if (mSocket == null || mSocket.isClosed()) {
            throw new Exception("Can't send the data, Socket is close or not created");
        }
        /* Sending data */
        try {
            mSocket.getOutputStream().write(data);
            mSocket.getOutputStream().flush();
        }
        catch (IOException e) {
            throw new Exception("Can't send the data: "+e.getMessage());
        }
    }

    public void closeConnection() {
        if (mSocket != null && !mSocket.isClosed()) {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Can't open socket: " + e.getMessage());         }
            finally {
                mSocket = null;
            }
        }
        mSocket = null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }
}
