package network;

import controller.FarmController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NetworkController {
    private Profile profile;
    private Reader reader;
    private Writer writer;

    public boolean addProfileAction(boolean isHost, int port, String ip, String name){
        profile = new Profile(isHost, name, port, ip);
        profile.setFarmController(new FarmController());
        profile.addLeaderBoard(name, profile.getFarmController().getFarm().getMoney());
        reader = new Reader(profile);
        writer = new Writer(profile);
        new Thread(reader).start();
        new Thread(writer).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterator iterator = profile.getLeaderBoard().entrySet().iterator();
        Map.Entry pair = (Map.Entry)iterator.next();
        while(iterator.hasNext()) {
            pair = (Map.Entry)iterator.next();
            if(pair.getKey().equals(name))
                return false;
        }
        return true;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void startChat(){

    }

    public void sendMessage(String message){

    }

    public HashMap<String, Integer> showLeaderBoard() {
        return profile.getLeaderBoard();
    }
}
