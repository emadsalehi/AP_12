package network;


import GUI.GraphicController;
import controller.FarmController;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Profile {

    private boolean isHost;
    private String profileName;
    private FarmController farmController;
    private int portNumber;
    private String serverIP;
    private ArrayList<Socket> profileSockets = new ArrayList<>();
    private HashMap<String, Integer> leaderBoard = new HashMap<>();
    private GraphicController graphicController;


    public Profile(boolean isHost, String ClientProfileName, int portNumber, String serverIP, GraphicController graphicController) {
        this.isHost = isHost;
        this.profileName = ClientProfileName;
        this.portNumber = portNumber;
        this.serverIP = serverIP;
        this.graphicController = graphicController;
        socketMaker();
    }

    public boolean isHost() {
        return isHost;
    }

    public String getProfileName() {
        return profileName;
    }

    public ArrayList<Socket> getProfileSockets() {
        return profileSockets;
    }

    public void socketMaker () {
        if (isHost) {
            ServerSocket profileServerSocket;
            try {
                profileServerSocket = new ServerSocket(portNumber);
                SocketThread socketThread = new SocketThread(profileServerSocket, this, graphicController);
                socketThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Socket socket = new Socket(serverIP, portNumber);
                profileSockets.add(socket);
                Reader reader = new Reader(this, socket, graphicController);
                Writer writer = new Writer(this, socket);
                new Thread(reader).start();
                new Thread(writer).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FarmController getFarmController() {
        return farmController;
    }

    public void setFarmController(FarmController farmController) {
        this.farmController = farmController;
    }

    public void addLeaderBoard(String name, Integer money) {
        leaderBoard.put(name, money);
    }

    public HashMap<String, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public void addProfileSocket (Socket socket) {
        profileSockets.add(socket);
    }
}
