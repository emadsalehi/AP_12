package view;
import java.util.Scanner;

public class View {
    public String getInput(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
    public void logNotEnoughMoney(){
        System.out.println("Not Enough Money");
    }

    public void logNoWildAnimalFound(){
        System.out.println("No Wild Animal Found");
    }

}
