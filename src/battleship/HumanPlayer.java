package battleship;

import javafx.stage.Stage;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumanPlayer extends Player{
    public HumanPlayer(String name){
        super(name);
        currMatchBoardPositions.setPlayer1(this);
    }
    private boolean ableToAttack = false;
    public Coordinates getCoordinates() {
        int row;
        int column;
        Scanner input = new Scanner(System.in);
        Pattern coordinatesPattern = Pattern.compile("[a-j],(([1-9])|(10))");
        Matcher matcher;
        boolean validInput = false;
        String userInput = ""; // no need to init but intellij disagrees
        while (!(validInput)) {
            userInput = input.next();
            matcher = coordinatesPattern.matcher(userInput);
            if (matcher.matches()) {
                validInput = true;
            }
        }
        String[] inputSplit = userInput.split(",");
        // The string (inputSplit[0]) is only 1 char long
        row = Coordinates.convertCharToRow(inputSplit[0].charAt(0));
        column = Coordinates.convertPIndex(Integer.parseInt(inputSplit[1]));
        // broken
        //input.close();
        return new Coordinates(row, column);
    }

    public void notify(String msg){
        setShipsPositions.setInstructions(msg);
    }

    public void setShips(){
        setShipsPositions.setInstructions("Please click where you would like to place the front of you ship");
        setShipsPositions.setPlayer(this);
    }

    public void placeShip(Coordinates start, Coordinates end, Stage primaryStage){
        Ship ship = board.getNextShip();
        boolean success = this.board.placeShip(ship,start,end);
        if (success){
            this.board.removeShip();
        }
        if(board.allShipsPlaced()){
            setShipsPositions.changeScene(primaryStage);
            this.bsg.mainGame();
        }
    }

    public void makeAttack(){
        notify("Click a spot on your opponent's board");
        ableToAttack = true;
    }
    public void GUIAttack(int i, int j){
        if (!ableToAttack){
            return;
        }
        boolean success = this.bsg.attack(this.board, this.enemyboard, new Coordinates(i,j));
        if (success){
            ableToAttack = false;
            enemyboard.getOwner().makeAttack();
        }
    }

    public void showBoardBeforeGame(){
        BoardTile[][] myboard = this.board.getViewableBoard(this);
        int n = this.board.getBoardSize();
        String[][] myBoardGUI = new String[n][n];
        for (int row = 0 ; row < n; row++){
            for (int column = 0; column < n; column++){
                myBoardGUI[row][column] = myboard[row][column].getSymbol();
            }
        }
        setShipsPositions.displayBoard(myBoardGUI);
    }
    public void showBoard(){ showBoardInGame();}
    public void showBoardInGame(){
        BoardTile[][] myboard = this.board.getViewableBoard(this);
        int n = this.board.getBoardSize();
        String[][] myBoardGUI = new String[n][n];
        for (int row = 0 ; row < n; row++){
            for (int column = 0; column < n; column++){
                myBoardGUI[row][column] = myboard[row][column].getSymbol();
            }
        }

        BoardTile[][] theirboard = this.enemyboard.getViewableBoard(this);
        String[][] enemyBoardGUI = new String[n][n];
        for (int row = 0 ; row < n; row++){
            for (int column = 0; column < n; column++){
                enemyBoardGUI[row][column] = theirboard[row][column].getSymbol();
            }
        }

        currMatchBoardPositions.displayLeftBoard(myBoardGUI);
        currMatchBoardPositions.displayRightBoard(enemyBoardGUI);
    }

}
