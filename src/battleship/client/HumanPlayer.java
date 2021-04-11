package battleship.client;

import battleship.*;
import javafx.stage.Stage;


public class HumanPlayer extends ClientPlayer {
    private String serverInfo = "";
    private Connection connection;
    public HumanPlayer(String name){
        super(name);
        currMatchBoardPositions.setPlayer1(this);
    }
    private boolean ableToAttack = false;

    public void notify(String msg){
        setShipsPositions.setInstructions(msg);
    }

    public void createBoard(){
        this.board = new ClientBoard(this);
        setShipsPositions.setInstructions("Please click where you would like to place the front of you ship");
        setShipsPositions.setPlayer(this);
    }

    public void placeShip(Coordinates start, Coordinates end, Stage primaryStage){
        Ship ship = board.getNextShip();
        boolean success = this.board.placeShip(ship,start,end);
        if (success){
            this.board.removeShip();
            serverInfo += start.toString() + "-" + end.toString();
            if (!board.allShipsPlaced()){
                serverInfo += "|";
            }
        }
        if(board.allShipsPlaced()){
            setShipsPositions.changeScene(primaryStage);
            this.connection.send(serverInfo);
            Thread t = new Thread(new SafeAwaitMessage(this.connection, this.battleShipGame));
            t.start();
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
        boolean success = this.battleShipGame.attack(this, new Coordinates(i,j));
        if (success){
            ableToAttack = false;
            this.connection.send(new Coordinates(j,i).toString());
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

        BoardTile[][] theirBoard = this.battleShipGame.getOtherPlayer(this).getViewableBoard(this);
        String[][] enemyBoardGUI = new String[n][n];
        for (int row = 0 ; row < n; row++){
            for (int column = 0; column < n; column++){
                enemyBoardGUI[row][column] = theirBoard[row][column].getSymbol();
            }
        }

        currMatchBoardPositions.displayLeftBoard(myBoardGUI);
        currMatchBoardPositions.displayRightBoard(enemyBoardGUI);
    }

    public void setConnection(Connection c){
        this.connection = c;
    }

}