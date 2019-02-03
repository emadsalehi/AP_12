package controller;
import model.Cat;
import model.Dog;
import model.FarmAnimal;
import model.FarmAnimalType;
import model.request.*;

import java.io.Serializable;

public class CommandAnalyzer implements Serializable {
    private final String BUY_REQUEST = "buy (cat|dog|cow|chicken|sheep)";  //Some Animals are absent in FarmAnimalType
    private final String PICKUP_REQUEST = "pickup ([1-9]|[1-2][0-9]|30) ([1-9]|[1-2][0-9]|30)";
    private final String CAGE_REQUEST = "cage ([1-9]|[1-2][0-9]|30) ([1-9]|[1-2][0-9]|30)";
    private final String PLANT_REQUEST = "plant ([1-9]|[1-2][0-9]|30) ([1-9]|[1-2][0-9]|30)";
    private final String WELL_REQUEST = "well";
    private final String START_REQUEST = "start (cookiebakery|customworkshop|spinnery|eggpowderplant|sewingfactory|" +
            "weavingfactory|cakebakery)";
    private final String UPGRADE_REQUEST = "upgrade (cookiebakery|customworkshop|spinnery|eggpowderplant|sewingfactory|" +
            "weavingfactory|cakebakery|cat|well|truck|helicopter|warehouse)";
    private final String LOAD_CUSTOM_REQUEST = "load custom (.*?)";
    private final String RUN_REQUEST = "run (.*?)";
    private final String SAVE_GAME_REQUEST = "save game (.*?)";
    private final String LOAD_GAME_REQUEST = "load game (.*?)";
    private final String PRINT_REQUEST = "print (info|map|levels|warehouse|well|workshops|truck|helicopter)";
    private final String TURN_REQUEST = "turn ([1-9]|[1-9][0-9]|[1-9][0-9][0-9])";
    private final String ADD_REQUEST = "(truck|helicopter) add (.*?) ([1-9]|[1-9][0-9])";
    private final String CLEAR_REQUEST = "(helicopter|truck) clear";
    private final String GO_REQUEST = "(helicopter|truck) go";

    public Request getRequest(String command) {
        if (command.matches(BUY_REQUEST)) {
            String[] params = command.split(" ");
            if(params[1].equals("cow") || params[1].equals("chicken") || params[1].equals("sheep")) {
                return new BuyRequest(new FarmAnimal((int)(Math.random() * 30) , (int)(Math.random() * 30) , FarmAnimalType.valueOf(params[1].toUpperCase())));
            }else if (params[1].equals("cat")){
                return new BuyRequest(new Cat((int)(Math.random() * 30) , (int)(Math.random() * 30)));
            }else{
                return new BuyRequest(new Dog((int)(Math.random() * 30) , (int)(Math.random() * 30)));
            }
        }else if (command.matches(PICKUP_REQUEST)) {
            String[] params = command.split(" ");
            return new PickUpRequest(Integer.parseInt(params[1]) , Integer.parseInt(params[2]));
        }else if (command.matches(CAGE_REQUEST)) {
            String[] params = command.split(" ");
            return new CageRequest(Integer.parseInt(params[1]), Integer.parseInt(params[2]));
        }else if (command.matches(PLANT_REQUEST)) {
            String[] params = command.split(" ");
            return new PlantRequest(Integer.parseInt(params[1]), Integer.parseInt(params[2]));
        }else if (command.matches(WELL_REQUEST)) {
            return new WellRequest();
        }else if (command.matches(START_REQUEST)) {
            String[] params = command.split(" ");
            return new StartRequest(params[1]);
        }else if (command.matches(UPGRADE_REQUEST)) {
            String[] params = command.split(" ");
            return new UpgradeRequest(params[1]);
        }else if (command.matches(LOAD_CUSTOM_REQUEST)) {
            String[] params = command.split(" " , 3);
            return new LoadCustomRequest(params[2]);
        }else if (command.matches(RUN_REQUEST)) {
            String[] params = command.split(" " , 2);
            return new RunRequest(params[1]);
        }else if (command.matches(SAVE_GAME_REQUEST)) {
            String[] params = command.split(" " , 3);
            return new SaveGameRequest(params[2]);
        }else if (command.matches(LOAD_GAME_REQUEST)) {
            String[] params = command.split(" " , 3);
            return new LoadGameRequest(params[2]);
        }else if (command.matches(PRINT_REQUEST)) {
            String[] params = command.split(" ");
            return new PrintRequest(params[1]);
        }else if (command.matches(TURN_REQUEST)) {
            String[] params = command.split(" ");
            return new TurnRequest(Integer.parseInt(params[1]));
        }else if (command.matches(ADD_REQUEST)) {
            String[] params = command.split(" ");
            return new AddRequest(params[0] , params[2] , Integer.parseInt(params[3]));
        }else if (command.matches(CLEAR_REQUEST)) {
            String[] params = command.split(" ");
            return new ClearRequest(params[0]);
        }else if (command.matches(GO_REQUEST)) {
            String[] params = command.split(" ");
            return new GoRequest(params[0]);
        }else {
            return new BadRequest();
        }
    }
}
