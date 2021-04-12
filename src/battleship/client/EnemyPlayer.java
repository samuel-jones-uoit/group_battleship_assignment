package battleship.client;

import battleship.*;


public class EnemyPlayer extends ClientPlayer {
    public EnemyPlayer(String name){
        super(name);
    }
    public void notify(String msg){
        setShipsPositions.setInstructions(msg);
    }

    public void makeAttack(Coordinates attackCoordinates){
        this.battleShipGame.attack(this, attackCoordinates); // always successful
    }

    public void setBoard(String s){
        this.board = ClientBoard.makeBoard(s, this);
    }

}
