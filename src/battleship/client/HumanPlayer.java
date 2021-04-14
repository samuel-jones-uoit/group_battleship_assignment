package battleship.client;

import battleship.*;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class HumanPlayer extends ClientPlayer {
    private String serverInfo = "";
    private Connection connection;
    private LinkedBlockingQueue<CustomInfo> stuffToDo;
    private boolean ableToAttack = false;
    public HumanPlayer(String name){
        super(name);
        stuffToDo = new LinkedBlockingQueue<>();
        HumanPlayer p = this;
        currMatchBoardPositions.setPlayer1(p);
    }

    private ClientGame getGame(){ return this.battleShipGame; }

    public void notify(String msg, String type){
        if (type.equals("beforeGame")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setShipsPositions.setInstructions(msg);
                }
            });
        }
    }

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

    public void notify(String msg){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currMatchBoardPositions.addMessage(msg);
            }
        });
    }

    public void createBoard(){
        this.board = new ClientBoard(this);
        HumanPlayer p = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setShipsPositions.setPlayer(p);
                Thread.currentThread().interrupt();
            }
        });
        PlaceShipInfo placeShipInfo;
        try{
            while (!board.outOfShips()){
                Ship ship = board.getNextShip();
                Integer size = ship.getSize();
                String name = ship.getName();
                this.notify("place your "+name+" it is "+size.toString()+" units long","beforeGame");
                placeShipInfo = (PlaceShipInfo) stuffToDo.take();
                placeShip(placeShipInfo.getPrev(), placeShipInfo.getNext(), placeShipInfo.getStage());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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
            System.out.println("Here");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("In runnable");
                    setShipsPositions.changeScene(primaryStage);
                    Thread.currentThread().interrupt();
                }
            });
            System.out.println("Sending stuff!!!");
            System.out.println(Thread.currentThread().getName());
            this.connection.send(serverInfo);
            this.battleShipGame.waitNextMessage();
        }
    }

    public void makeAttack(){
        notify("Click a spot on your opponent's board");
        ableToAttack = true;
        try{
            // handles failed attacks
            while(ableToAttack){
                AttackInfo a = (AttackInfo) this.stuffToDo.take();
                GUIAttack(a.getCoordinates());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void GUIAttack(Coordinates c){
        boolean success = this.battleShipGame.attack(this, c);
        if (success){
            ableToAttack = false;
            this.connection.send(c.toString());
            EnemyPlayer p = (EnemyPlayer) this.battleShipGame.getOtherPlayer(this);
            try{
                System.out.println(this.connection.receive()); // message about whatever hit battleship
                p.setBoard(this.connection.receive());
                showBoard();
            }catch (IOException e){
                e.printStackTrace();
            }
            battleShipGame.waitNextMessage();
        }
    }

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
                Thread.currentThread().interrupt();
            }
        });
    }

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
                Thread.currentThread().interrupt();
            }
        });

    }

    public void setConnection(Connection c){
        this.connection = c;
    }

    public void addToDo(CustomInfo c){
        if (stuffToDo.isEmpty()){
            stuffToDo.add(c);
        }
    }

}
