package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;

public class Writer implements Runnable {

    private Profile profile;
    private Socket socket;

    public Writer(Profile profile, Socket socket) {
        this.profile = profile;
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Formatter formatter = new Formatter(outputStream);
        while (true){
            formatter.format("leaderboard#" + profile.getProfileName() + "#" +
                    Integer.valueOf(profile.getFarmController().getFarm().getMoney()).toString() + "\n");
            formatter.flush();
            profile.addLeaderBoard(profile.getProfileName(), profile.getFarmController().getFarm().getMoney());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
