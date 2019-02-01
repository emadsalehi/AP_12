package network;


public class NetworkController {
    private Profile profile;
    private Reader reader;
    private Writer writer;

    public boolean addProfileAction(){
        return false;
    }


    public void startChat(){
        this.reader = new Reader();
        new Thread(reader).start();
    }

    public void sendMessage(String message){

    }

}
