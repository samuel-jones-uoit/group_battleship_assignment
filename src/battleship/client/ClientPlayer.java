package battleship.client;

import battleship.BoardTile;
import battleship.Coordinates;
import battleship.Player;

public abstract class ClientPlayer extends Player {
    protected ClientGame battleShipGame;
    protected ClientBoard board;
    public ClientPlayer(String name) {
        super(name);
    }


    public void setBattleShipGame(ClientGame c){
        this.battleShipGame = c;
    }

    public void notify(String msg, String type){}

    public BoardTile[][] getViewableBoard(ClientPlayer p){
        if (this.board == null){
            System.out.println(this.name);
        }
        return this.board.getViewableBoard(p);
    }

    public void hitSpot(Coordinates attackCoordinates) {
        this.board.hitSpot(attackCoordinates);
    }
}
