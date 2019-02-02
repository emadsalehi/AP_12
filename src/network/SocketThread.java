package network;

import GUI.GraphicController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread extends Thread {

    private ServerSocket serverSocket;
    private Profile profile;
    private GraphicController graphicController;

    public SocketThread(ServerSocket serverSocket, Profile profile, GraphicController graphicController) {
        this.serverSocket = serverSocket;
        this.profile = profile;
        this.graphicController = graphicController;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                profile.addProfileSocket(socket);
                Reader reader = new Reader(profile, socket, graphicController);
                Writer writer = new Writer(profile, socket);
                new Thread(reader).start();
                new Thread(writer).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
