package battleship.client;

import battleship.*;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
/**
 *   Class Name: HumanPlayer
 *   Class Description:
 *   Type of ClientPlayer -- user
 */
public class HumanPlayer extends ClientPlayer {
    private String serverInfo = ""; // used to string representation of board
    private Connection connection;
    private final LinkedBlockingQueue<CustomInfo> stuffToDo; // used for blocking feature
    private boolean ableToAttack = false;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   String name:
     *      Name
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public HumanPlayer(String name){
        super(name);
        stuffToDo = new LinkedBlockingQueue<>();
        HumanPlayer p = this;
        currMatchBoardPositions.setPlayer1(p);
    }

    /**
     *   Method Name: getGame
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: ClientGame
     */
    private ClientGame getGame(){ return this.battleShipGame; }

    /**
     *   Method Name: notify
     *   Method Parameters:
     *   String msg:
     *      Message
     *   String type:
     *      Type of message
     *   Method Description:
     *   Notifies the user of something
     *   Method Return: None
     */
    public void notify(String msg, String type){
        // if message is sent before the main game scene is used
        if (type.equals("beforeGame")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setShipsPositions.setInstructions(msg);
                }
            });
        }
    }

    /**
     *   Method Name: notifyError
     *   Method Parameters:
     *   String msg:
     *      Message
     *   String type:
     *      Type of message
     *   Method Description:
     *   Notifies the user of an error
     *   Method Return: None
     */
    public void notifyError(String msg, String type){
        if (type.equals("beforeGame")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setShipsPositions.setError(msg);
                }
            });
        }
    }

    /**
     *   Method Name: notify
     *   Method Parameters:
     *   String msg:
     *      Message
     *   Method Description:
     *   Notifies the user of something during the game
     *   Method Return: None
     */
    public void notify(String msg){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currMatchBoardPositions.addMessage(msg);
            }
        });
    }

    /**
     *   Method Name: createBoard
     *   Method Parameters: None
     *   Method Description:
     *   Allows the user to create their board
     *   Method Return: None
     */
    public void createBoard(){
        this.board = new ClientBoard(this);
        setShipsPositions.setPlayer(this);
        PlaceShipInfo placeShipInfo;
        try{
            // while ships still left to place
            while (!board.outOfShips()){
                Ship ship = board.getNextShip();
                int size = ship.getSize();
                String name = ship.getName();
                this.notify("place your " + name + " it is " + size + " units long","beforeGame");
                placeShipInfo = (PlaceShipInfo) stuffToDo.take();
                placeShip(placeShipInfo.getEnd1(), placeShipInfo.getEnd2(), placeShipInfo.getStage());
            }
        }catch (InterruptedException e){
            battleShipGame.gameOver("Unknown error.");
        }
    }

    /**
     *   Method Name: placeShip
     *   Method Parameters:
     *   Coordinates start:
     *      Start of ship placement
     *   Coordinates end:
     *      End of ship placement
     *   Stage primaryStage:
     *      Stage used when changing scenes
     *   Method Description:
     *   Places a ship on the user's board
     *   Method Return: None
     */
    public void placeShip(Coordinates start, Coordinates end, Stage primaryStage){
        Ship ship = board.getNextShip();
        boolean success = this.board.placeShip(ship,start,end);
        // if it successfully places
        if (success){
            this.board.removeShip();
            // get ready to send info to server
            serverInfo += start.toString() + "-" + end.toString();
            if (!board.allShipsPlaced()){
                serverInfo += "|";
            }
        }
        // if they are now done placing ships
        if(board.allShipsPlaced()){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setShipsPositions.changeScene(primaryStage);
                }
            });
            // send a string representation of board to the server
            this.connection.send(serverInfo);
            this.battleShipGame.waitNextMessage();
        }
    }

    /**
     *   Method Name: makeAttack
     *   Method Parameters: None
     *   Method Description:
     *   User attacks
     *   Method Return: None
     */
    public void makeAttack(){
        notify("Click a spot on your opponent's board");
        ableToAttack = true;
        try{
            // while because attack could fail and user tries again
            while(ableToAttack){
                AttackInfo a = (AttackInfo) this.stuffToDo.take();
                GUIAttack(a.getCoordinates());
            }
        }catch (InterruptedException e){
            battleShipGame.gameOver("Unknown error.");
        }
    }

    /**
     *   Method Name: GUIAttack
     *   Method Parameters:
     *   Coordinates c:
     *      coordinates of target
     *   Method Description:
     *   User attacks
     *   Method Return: None
     */
    public void GUIAttack(Coordinates c){
        boolean success = this.battleShipGame.attack(this, c);
        if (success){
            ableToAttack = false;
            this.connection.send(c.toString());
            EnemyPlayer p = (EnemyPlayer) this.battleShipGame.getOtherPlayer(this);
            try{
                notify(this.connection.receive()); // message about whatever hit battleship
                p.setBoard(this.connection.receive());
                showBoard();
            }catch (IOException e){
                battleShipGame.gameOver("Network error.");
            }
            battleShipGame.waitNextMessage();
        }
    }

    /**
     *   Method Name: showBoardBeforeGame
     *   Method Parameters: None
     *   Method Description:
     *   Shows the board (before the game starts)
     *   Method Return: None
     */
    public void showBoardBeforeGame(){
        BoardTile[][] myBoard = this.board.getViewableBoard(this);
        int n = this.board.getBoardSize();
        String[][] myBoardGUI = new String[n][n];
        for (int row = 0 ; row < n; row++){
            for (int column = 0; column < n; column++){
                myBoardGUI[row][column] = myBoard[row][column].getSymbol();
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setShipsPositions.displayBoard(myBoardGUI);
            }
        });
    }
    /**
     *   Method Name: showBoard
     *   Method Parameters: None
     *   Method Description:
     *   Shows the user & enemy boards
     *   Method Return: None
     */
    public void showBoard(){
        BoardTile[][] myBoard = this.board.getViewableBoard(this);
        int n = this.board.getBoardSize();
        String[][] myBoardGUI = new String[n][n];
        for (int row = 0 ; row < n; row++){
            for (int column = 0; column < n; column++){
                myBoardGUI[row][column] = myBoard[row][column].getSymbol();
            }
        }

        BoardTile[][] enemyBoard = this.battleShipGame.getOtherPlayer(this).getViewableBoard(this);
        String[][] enemyBoardGUI = new String[n][n];
        for (int row = 0 ; row < n; row++){
            for (int column = 0; column < n; column++){
                enemyBoardGUI[row][column] = enemyBoard[row][column].getSymbol();
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currMatchBoardPositions.displayLeftBoard(myBoardGUI);
                currMatchBoardPositions.displayRightBoard(enemyBoardGUI);
            }
        });

    }
    /**
     *   Method Name: setConnection
     *   Method Parameters:
     *   Connection c:
     *      Connection used by the player
     *   Method Description:
     *   Setter
     *   Method Return: None
     */
    public void setConnection(Connection c){
        this.connection = c;
    }

    /**
     *   Method Name: addToDo
     *   Method Parameters:
     *   CustomInfo c:
     *      Either place ship or attack a spot
     *   String exception:
     *      Usually only executes if user is able to attack but before game needs to work too so there's exception
     *   Method Description:
     *   Handles tasks from the GUI
     *   Method Return: None
     */
    public void addToDo(CustomInfo c, String exception){
        // Exception as in exception to the rule
        if (stuffToDo.isEmpty() && (ableToAttack || exception.equals("b4"))){
            stuffToDo.add(c);
        }
    }

    /**
     *   Method Name: addToDo
     *   Method Parameters:
     *   CustomInfo c:
     *   Method Description:
     *   Handles tasks from the GUI
     *   Method Return: None
     */
    public void addToDo(CustomInfo c){
        addToDo(c, "");
    }

}
