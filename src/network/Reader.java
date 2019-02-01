package network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Reader implements Runnable {
    private Profile profile

    public Reader(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        final String TEXT = "text#(.*?)";
        final String LEADERBOARD = "leaderboard#(.*?)";

        try {
            inputStream = profile.getProfileSocket().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);
        while (true){
            String str = scanner.nextLine();
            if (str.matches(TEXT)){
                String[] params = str.split("#");
            }else if (str.matches(LEADERBOARD)){
                String[] params = str.split("#");
                profile.addLeaderBoard(params[1] , Integer.valueOf(params[2]));
            }
        }


    }
}
