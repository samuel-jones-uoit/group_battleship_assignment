package battleship;

import java.util.Scanner;
import java.util.regex.Pattern;

public class HumanPlayer extends Player{
    public HumanPlayer(String name){
        super(name);
    }

    public Coordinates getCoordinates() {
        int row;
        int column;
        Scanner input = new Scanner(System.in);
        Pattern coordinatesPattern = Pattern.compile("[a-j], [1-10]");
        String userInput = input.next(coordinatesPattern);
        String[] inputSplit = userInput.split(", ");
        // The string (inputSplit[0]) is only 1 char long
        row = Coordinates.convertRowToChar(inputSplit[0].charAt(0));
        column = Coordinates.convertPIndex(Integer.parseInt(inputSplit[1]));
        input.close();
        return new Coordinates(row, column);
    }

    public void notify(String msg){
        System.out.println(msg);
    }
}
