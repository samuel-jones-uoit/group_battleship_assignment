package battleship.client;

import battleship.BoardTile;
import battleship.Coordinates;
import battleship.Player;

public abstract class ClientPlayer extends Player {
    protected ClientBoard board;
    protected ClientGame battleShipGame;
    public ClientPlayer(String name) {
        super(name);
    }

    public boolean isAlive() {
        return this.board.isAlive();
    }
    public BoardTile[][] getViewableBoard(ClientPlayer p){
        return this.board.getViewableBoard(p);
    }

    public void hitSpot(Coordinates attackCoordinates) {
        this.board.hitSpot(attackCoordinates);
    }

    public void setBattleShipGame(ClientGame c){
        this.battleShipGame = c;
    }
}
