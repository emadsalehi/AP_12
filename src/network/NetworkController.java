package network;

import java.io.*;
import java.net.Socket;
import java.util.Formatter;

import GUI.GraphicController;
import com.gilecode.yagson.YaGson;
import controller.FarmController;

import java.util.HashMap;
import java.util.Scanner;


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

    public synchronized void sendBazaar(Socket socket){
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/bazaar.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);
        String bazaarJsonString = scanner.nextLine();

        Formatter formatter = new Formatter(outputStream);
        formatter.format("bazaar#" + bazaarJsonString + "\n");
        formatter.flush();
    }
    

    public HashMap<String, Integer> showLeaderBoard() {
        return profile.getLeaderBoard();
    }

    public Profile getProfile() {
        return profile;
    }

}
