package network;

import GUI.GraphicController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
