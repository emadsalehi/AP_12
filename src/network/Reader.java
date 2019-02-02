package network;

import GUI.GraphicController;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Reader implements Runnable {
    private Profile profile;
    private GraphicController graphicController;

    public Reader(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        final String TEXT = "text#(.*?)";
        final String LEADERBOARD = "leaderboard#(.*?)";
        try {
            synchronized (profile) {
                profile.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            inputStream = profile.getProfileSocket().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);
        while (true){
            String str = scanner.nextLine();
            String[] params = str.split("#");
            if (params[0].equals("text")){
                graphicController.showMessage(params[2], params[1]);
            }else if (params[0].equals("leaderboard")){
                profile.addLeaderBoard(params[1] , Integer.valueOf(params[2]));
            }
        }
    }

    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = graphicController;
    }
}
