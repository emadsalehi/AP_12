package network;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;

public class NetworkController {
    private Profile profile;
    private Reader reader;
    private Writer writer;

    public boolean addProfileAction(){
        this.reader = new Reader();
        new Thread(reader).start();
        return false;
    }


    public void startChat(){

    }

    public void sendMessage(String message){
        Socket socket = profile.getProfileSocket();
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Formatter formatter = new Formatter(outputStream);
        formatter.format("text#" + profile.getProfileName() + "#" + message + "\n");
        formatter.flush();
        formatter.close();
    }

}
