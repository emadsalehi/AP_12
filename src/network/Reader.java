package network;

import GUI.GraphicController;
import com.gilecode.yagson.YaGson;

import java.io.*;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Reader implements Runnable {
    private Profile profile;
    private Socket socket;
    private GraphicController graphicController;

    public Reader(Profile profile, Socket socket,GraphicController graphicController) {
        this.profile = profile;
        this.socket = socket;
        this.graphicController = graphicController;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);
        while (true){
            String str = scanner.nextLine();
            String[] params = str.split("#");

            if (params[0].equals("text"))
                graphicController.showMessage(params[1], params[2]);
            else if (params[0].equals("leaderboard"))
                profile.addLeaderBoard(params[1] , Integer.valueOf(params[2]));
            else if (params[0].equals("bazaar")){
                YaGson yaGson = new YaGson();
                profile.setBazaar(yaGson.fromJson(params[1], Bazaar.class));
//                String bazaarJson = params[1];
//                FileOutputStream fileOutputStream = null;
//                try {
//                    fileOutputStream = new FileOutputStream("/bazaar.json");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                PrintWriter writer = new PrintWriter(fileOutputStream);
//                writer.print("");
//                writer.close();
//                OutputStream out = null;
//                try {
//                    out = new FileOutputStream("/bazaar.json");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Formatter formatter = new Formatter(out);
//                formatter.format(bazaarJson);
//                formatter.flush();
            }
            if(profile.isHost()) {
                for (Socket socket : profile.getProfileSockets()) {
                    try {
                        outputStream = socket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Formatter formatter = new Formatter(outputStream);
                    formatter.format(str + "\n");
                    formatter.flush();
                }
            }
        }
    }
}
