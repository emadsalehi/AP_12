package controller;
import model.*;
import model.request.StartRequest;
import view.View;

public class FarmController {
    private Farm farm = new Farm();
    private View view = new View();
    private CommandAnalyzer commandAnalyzer = new CommandAnalyzer();

    public void listenForCommand() {
        String command = view.getInput();
    }

    public void addAction() {

    }

    public void buyAction() {

    }

    public void cageAction() {

    }

    public void clearAction() {

    }

    public void goAction() {

    }

    public void loadCustomAction() {

    }

    public void loadGameAction() {

    }

    public void pickUpAction() {

    }

    public void plantAction() {

    }

    public void printAction() {

    }

    public void runAction() {

    }

    public void saveGameAction() {

    }

    public void startAction(StartRequest request) {
        //todo custom workshop not handled.
        WorkShop selectedWorkShop = null;
        for (WorkShop workShop : farm.getWorkShops()) {
            if (request.getWorkShopName().equals("SewingFactory") && workShop instanceof SewingFactory)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("Spinnery") && workShop instanceof Spinnery)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("EggPowderPlant") && workShop instanceof EggPowderPlant)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("WeavingFactory") && workShop instanceof WeavingFactory)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("CookieBakery") && workShop instanceof CookieBakery)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("CakeBakery") && workShop instanceof CakeBakery)
                selectedWorkShop = workShop;
            else {
                view.logWrongCommand();
                return;
            }
        }
        if (selectedWorkShop.isWorking()) {
            view.logWorkShopIsWorking();
            return;
        }
        selectedWorkShop.startWorkShop();
    }

    public void turnAction() {

    }

    public void upgradeAction() {
        
    }

    public void wellAction() {

    }
}
