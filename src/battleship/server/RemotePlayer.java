package battleship.server;

import battleship.*;

import java.io.IOException;

/**
 *   Method Name: RemotePlayer
 *   Method Parameters:
 *   Method Description:
 *   Regular constructor
 *   Method Return: None
 */
public class RemotePlayer extends Player{
    private final Connection connection;
    private ServerBoard board;
    private final GameHandler game;
    /**
     *   Method Name: RemotePlayer
     *   Method Parameters:
     *   String name:
     *      Name of remote player
     *   Connection connection:
     *      connection to player
     *   GameHandler game:
     *      Associated game instance
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public RemotePlayer(String name, Connection connection, GameHandler game){
        super(name);
        this.connection = connection;
        this.game = game;
    }

    /**
     *   Method Name: createBoard
     *   Method Parameters: None
     *   Method Description:
     *   Creates a board for a player
     *   Method Return: None
     */
    public void createBoard() throws IOException {
        this.board = new ServerBoard(this);
        assert this.connection != null;
        String instructions = this.connection.receive();
        String[] instructionsSplit = instructions.split("\\|");
        Ship[] shipsToPlace = this.board.getShipsToPlace();
        int i = 0;
        // Decipher coordinates to place ships
        for (String twoCoordinates : instructionsSplit){
            Coordinates[] coords = decipherTwo(twoCoordinates);
            this.board.placeShip(shipsToPlace[i], coords[0], coords[1]);
            i++;
        }
    }

    /**
     *   Method Name: decipherTwo
     *   Method Parameters:
     *   String twoCoordinates:
     *      Two coordinates represented as string
     *   Method Description:
     *   Creates 2 coordinates from a string
     *   Method Return: Coordinates[]
     */
    private Coordinates[] decipherTwo(String twoCoordinates){
        Coordinates[] coords = new Coordinates[2];
        String[] coordinatesSplit = twoCoordinates.split("-");
        coords[0] = Coordinates.fromString(coordinatesSplit[0]);
        coords[1] = Coordinates.fromString(coordinatesSplit[1]);
        return coords;
    }

    /**
     *   Method Name: notify
     *   Method Parameters:
     *   String msg:
     *      Message to send to a player
     *   Method Description:
     *   Notify player
     *   Method Return: None
     */
    public void notify(String msg){
        assert this.connection != null;
        this.connection.send(msg);
    }

    /**
     *   Method Name: makeAttack
     *   Method Parameters: None
     *   Method Description:
     *   Allows player to make an attack
     *   Method Return: None
     */
    public void makeAttack() throws IOException{
        assert this.connection != null;
        this.connection.send("YOUR_MOVE");
        Coordinates c = Coordinates.fromString(this.connection.receive());
        this.game.attack(this, c);
        this.notify(this.game.getOtherPlayer(this).getBoard().toString(this));
    }

    /**
     *   Method Name: hitSpot
     *   Method Parameters:
     *   Coordinates attackCoordinates:
     *      Coordinates of spot to hit
     *   RemotePlayer attack:
     *      Attacker hitting a spot
     *   Method Description:
     *      Hit a spot on a board
     *   Method Return: None
     */
    public void hitSpot(Coordinates attackCoordinates, RemotePlayer attacker){
        assert this.connection != null;
        this.connection.send("ATTACK_ON_YOU");
        this.connection.send(attackCoordinates.toString());
        this.board.hitSpot(attackCoordinates, attacker);
    }

    /**
     *   Method Name: getBoard
     *   Method Parameters: None
     *   Method Description:
     *   Get the board of the player
     *   Method Return: ServerBoard
     */
    public ServerBoard getBoard(){ return this.board; }

    /**
     *   Method Name: isAlive
     *   Method Parameters: None
     *   Method Description:
     *   Finds out of player is alive
     *   Method Return: boolean (isAlive)
     */
    public boolean isAlive(){
        return this.board.isAlive();
    }

    /**
     *   Method Name: closeConnection
     *   Method Parameters: None
     *   Method Description:
     *   Closes a connection
     *   Method Return: None
     */
    public void closeConnection(){
        try{
            this.connection.close();
        }catch (IOException e){
            System.err.println("Network error.");
            this.game.shutDown();
        }
    }
}
