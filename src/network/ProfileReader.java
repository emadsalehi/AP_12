package network;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Formatter;

public class ProfileReader implements Runnable {
    private Profile profile;
    private Socket socket;
    private String name;

    public ProfileReader(Profile profile, Socket socket, String name) {
        this.profile = profile;
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                Profile readedProfile = (Profile) objectInputStream.readObject();
                if(readedProfile.getProfileName().equals(name)) {
                    profile.addToFriends(readedProfile);
                    break;
                }
                if(profile.isHost()) {
                    for (Socket socket : profile.getProfileSockets()) {
                        try {
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream.writeObject(readedProfile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
