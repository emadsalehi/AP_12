package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread implements Runnable {

    ServerSocket serverSocket;
    Socket socket;

    public SocketThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            this.socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
