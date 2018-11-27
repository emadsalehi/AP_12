package view;
import java.util.Scanner;

public class View {
    public String getInput(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}
