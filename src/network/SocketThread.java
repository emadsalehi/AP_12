package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread extends Thread {

    ServerSocket serverSocket;
    Profile profile;

    public SocketThread(ServerSocket serverSocket, Profile profile) {
        this.serverSocket = serverSocket;
        this.profile = profile;
    }

    @Override
    public void run() {
        try {
            profile.setProfileSocket(serverSocket.accept());
            synchronized (profile) {
                profile.notifyAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
