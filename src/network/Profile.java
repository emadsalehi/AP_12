package network;


import controller.FarmController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Profile {

    private boolean isHost;
    private String profileName;
    private int money;
    private int portNumber;
    private String serverIP;
    private Socket profileSocket;
    private FarmController farmController;


    public Profile(boolean isHost, String ServerProfileName, int money, int serverPortNumber) {
        this.isHost = isHost;
        this.profileName = ServerProfileName;
        this.money = money;
        this.portNumber = serverPortNumber;
        if (isHost) {
            serverIP = ("localhost");
        }
        this.profileSocket = socketMaker();

    //  todo: writers and readers initializing

    }

    public Profile(boolean isHost, String ClientProfileName, int money, int portNumber, String serverIP) {
        this.isHost = isHost;
        this.profileName = ClientProfileName;
        this.money = money;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Socket getProfileSocket() {
        return profileSocket;
    }

    public void setProfileSocket(Socket profileSocket) {
        this.profileSocket = profileSocket;
    }

    public FarmController getFarmController() {
        return farmController;
    }

    public void setFarmController(FarmController farmController) {
        this.farmController = farmController;
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


}
