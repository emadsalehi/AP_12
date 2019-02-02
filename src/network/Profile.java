package network;


import controller.FarmController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Profile {

    private boolean isHost;
    private String profileName;
    private FarmController farmController;
    private int portNumber;
    private String serverIP;
    private Socket profileSocket;
    private HashMap<String, Integer> leaderBoard = new HashMap<>();

    public Profile(boolean isHost, String ServerProfileName, int serverPortNumber) {
        this.isHost = isHost;
        this.profileName = ServerProfileName;
        this.portNumber = serverPortNumber;
        if (isHost) {
            serverIP = ("localhost");
        }
        this.profileSocket = socketMaker();
    }

    public Profile(boolean isHost, String ClientProfileName, int portNumber, String serverIP) {
        this.isHost = isHost;
        this.profileName = ClientProfileName;
        this.portNumber = portNumber;
        this.serverIP = serverIP;
        this.profileSocket = socketMaker();

    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }


    public Socket socketMaker () {
        if (isHost) {
            ServerSocket profileServerSocket = null;
            try {
                profileServerSocket = new ServerSocket(portNumber);
                return profileServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return new Socket(serverIP, portNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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
}
