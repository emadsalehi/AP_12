package network;

import controller.FarmController;

import java.util.HashMap;


public class NetworkController {
    private Profile profile;

    public void addProfileAction(boolean isHost, int port, String ip, String name){
        profile = new Profile(isHost, name, port, ip);
        profile.setFarmController(new FarmController());
        profile.addLeaderBoard(name, profile.getFarmController().getFarm().getMoney());
    }


    public void startChat(){

    }

    public void sendMessage(String message){

    }

    public HashMap<String, Integer> showLeaderBoard() {
        return profile.getLeaderBoard();
    }

    public Profile getProfile() {
        return profile;
    }
}
