package network;


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

    }

}
