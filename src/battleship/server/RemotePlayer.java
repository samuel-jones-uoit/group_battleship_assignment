package battleship.server;

import battleship.*;

import java.io.IOException;


public class RemotePlayer extends Player{
    private final Connection connection;
    private ServerBoard board;
    private GameHandler game;
    public RemotePlayer(String name, Connection connection, GameHandler game){
        super(name);
        this.connection = connection;
        this.game = game;
    }

    public void createBoard() throws IOException {
        this.board = new ServerBoard(this);
        assert this.connection != null;
        String instructions = this.connection.receive();
        String[] instructionsSplit = instructions.split("\\|");
        Ship[] shipsToPlace = this.board.getShipsToPlace();
        int i = 0;
        for (String twoCoordinates : instructionsSplit){
            Coordinates[] coords = decipherTwo(twoCoordinates);
            this.board.placeShip(shipsToPlace[i], coords[0], coords[1]);
            i++;
        }
    }

    private Coordinates[] decipherTwo(String twoCoordinates){
        Coordinates[] coords = new Coordinates[2];
        String[] coordinatesSplit = twoCoordinates.split("-");
        coords[0] = Coordinates.fromString(coordinatesSplit[0]);
        coords[1] = Coordinates.fromString(coordinatesSplit[1]);
        return coords;
    }
    public void notify(String msg){
        assert this.connection != null;
        this.connection.send(msg);
    }

    public void makeAttack() throws IOException{
        assert this.connection != null;
        this.connection.send("YOUR_MOVE");
        Coordinates c = Coordinates.fromString(this.connection.receive());
        this.game.attack(this, c);
        this.notify(this.game.getOtherPlayer(this).getBoard().toString(this));
    }

    public void hitSpot(Coordinates attackCoordinates, RemotePlayer attacker){
        assert this.connection != null;
        this.connection.send("ATTACK_ON_YOU");
        this.connection.send(attackCoordinates.toString());
        this.board.hitSpot(attackCoordinates, attacker);
    }

    public ServerBoard getBoard(){ return this.board; }

    public boolean isAlive(){
        return this.board.isAlive();
    }
    public void print(){ this.board.display(this); }
    public void closeConnection(){
        try{
            this.connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
