package controller;
import model.*;
import model.request.*;
import view.View;

public class FarmController {
    private Farm farm = new Farm();
    private View view = new View();
    private CommandAnalyzer commandAnalyzer = new CommandAnalyzer();

    public void listenForCommand() {
        boolean requestsOnTheWay = true;
        while (requestsOnTheWay) {
            String command = view.getInput().trim();
            Request request = commandAnalyzer.getRequest(command);
            if (request instanceof AddRequest){
                addAction((AddRequest) request);
            }else if (request instanceof BadRequest){
                badAction((BadRequest) request);
            }else if (request instanceof BuyRequest){
                buyAction((BuyRequest) request);
            }else if (request instanceof CageRequest){
                cageAction((CageRequest) request);
            }else if (request instanceof ClearRequest){
                clearAction((ClearRequest) request);
            }else if (request instanceof GoRequest){
                goAction((GoRequest) request);
            }else if (request instanceof LoadCustomRequest){
                loadCustomAction((LoadCustomRequest) request);
            }else if (request instanceof LoadGameRequest){
                loadGameAction((LoadGameRequest) request);
            }else if (request instanceof PickUpRequest){
                pickUpAction((PickUpRequest) request);
            }else if (request instanceof PlantRequest){
                plantAction((PlantRequest) request);
            }else if (request instanceof PrintRequest){
                printAction((PrintRequest) request);
            }else if (request instanceof RunRequest){
                runAction((RunRequest) request);
            }else if (request instanceof SaveGameRequest){
                saveGameAction((SaveGameRequest) request);
            }else if (request instanceof StartRequest) {
                startAction((StartRequest) request);
            }else if (request instanceof TurnRequest){
                turnAction((TurnRequest) request);
            }else if (request instanceof UpgradeRequest){
                upgradeAction((UpgradeRequest) request);
            }else if (request instanceof WellRequest){
                wellAction((WellRequest) request);
            }else if (request instanceof ExitRequest){
                requestsOnTheWay = false;
            }
        }

    }

    public void addAction(AddRequest request) {

    }

    public void badAction(BadRequest request) {

    }

    public void buyAction(BuyRequest request) {

    }

    public void cageAction(CageRequest request) {

    }

    public void clearAction(ClearRequest request) {

    }

    public void goAction(GoRequest request) {

    }

    public void loadCustomAction(LoadCustomRequest request) {

    }

    public void loadGameAction(LoadGameRequest request) {

    }

    public void pickUpAction(PickUpRequest request) {

    }

    public void plantAction(PlantRequest  request) {

    }

    public void printAction(PrintRequest request) {

    }

    public void runAction(RunRequest request) {

    }

    public void saveGameAction(SaveGameRequest request) {

    }

    public void startAction(StartRequest request) {

    }

    public void turnAction(TurnRequest request) {

    }

    public void upgradeAction(UpgradeRequest request) {

    }

    public void wellAction(WellRequest request) {

    }
}
