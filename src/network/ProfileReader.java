package network;


import com.gilecode.yagson.YaGson;

import java.io.*;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

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
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);
        YaGson yaGson = new YaGson();
        while(true) {
            String str = scanner.nextLine();
            String[] params = str.split("#");
            if(params[0].equals("profile")) {
                if(params[1].equals(name)) {
                    profile.addToFriends(params[1]);
                    break;
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
}
