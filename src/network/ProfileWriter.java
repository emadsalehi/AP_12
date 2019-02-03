package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ProfileWriter implements Runnable {
    private Profile profile;
    private Socket socket;

    public ProfileWriter(Profile profile, Socket socket) {
        this.profile = profile;
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                objectOutputStream.writeObject(profile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
