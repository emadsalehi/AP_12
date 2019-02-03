package network;


import GUI.GraphicController;
import controller.FarmController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile {

    private boolean isHost;
    private String profileName;
    private FarmController farmController;
    private int portNumber;
    private String serverIP;
    private ArrayList<Socket> profileSockets = new ArrayList<>();
    private HashMap<String, Integer> leaderBoard = new HashMap<>();
    private GraphicController graphicController;
    private ArrayList<Profile> friends = new ArrayList<>();
    private boolean isUnique = true;


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
                new Thread(reader).start();
                Thread.sleep(3000);
                for (Map.Entry<String, Integer> entry : leaderBoard.entrySet()) {
                    if (entry.getKey().equals(profileName))
                        isUnique = false;
                }
                if(isUnique) {
                    Writer writer = new Writer(this, socket);
                    new Thread(writer).start();
                    ProfileWriter profileWriter = new ProfileWriter(this, socket);
                    new Thread(profileWriter).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addFriend(String name){
        ProfileReader profileReader = new ProfileReader(this, profileSockets.get(0), name);
        new Thread(profileReader).start();
    }

    public void showFriends() {
        for (Profile profile : friends) {
            System.out.println(profile.getProfileName());
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

    public boolean isUnique() {
        return isUnique;
    }

    public void addToFriends(Profile profile) {
        friends.add(profile);
    }
}
