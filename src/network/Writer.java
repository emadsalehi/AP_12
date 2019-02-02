package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;

public class Writer implements Runnable {

    private Profile profile;

    public Writer(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void run() {
        try {
            synchronized (profile) {
                profile.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = null;
        try {
            outputStream = profile.getProfileSocket().getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Formatter formatter = new Formatter(outputStream);
        while (true){
            formatter.format("leaderboard#" + profile.getProfileName() + "#" +
                    (new Integer(profile.getFarmController().getFarm().getMoney())).toString());
            formatter.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
