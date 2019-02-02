package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;

import GUI.GraphicController;
import controller.FarmController;

import java.util.HashMap;


public class NetworkController {
    private Profile profile;

    public void addProfileAction(boolean isHost, int port, String ip, String name, GraphicController graphicController){
        profile = new Profile(isHost, name, port, ip, graphicController);
        profile.setFarmController(new FarmController());
        profile.addLeaderBoard(name, profile.getFarmController().getFarm().getMoney());
    }

    public void startChat(){

    }

    public synchronized void sendMessage(String message){
        for(Socket socket : profile.getProfileSockets()) {
            OutputStream outputStream = null;
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Formatter formatter = new Formatter(outputStream);
            formatter.format("text#" + profile.getProfileName() + "#" + message + "\n");
            formatter.flush();
        }
    }

    public HashMap<String, Integer> showLeaderBoard() {
        return profile.getLeaderBoard();
    }

    public Profile getProfile() {
        return profile;
    }

}
