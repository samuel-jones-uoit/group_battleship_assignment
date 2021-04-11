/*
package battleship;

import java.util.Random;
public class BotPlayer extends Player{
    private Random random = new Random();
    public BotPlayer(String name){
        super(name);
    }

    private Coordinates getCoordinates(){
        return new Coordinates(random.nextInt(10),random.nextInt(10));
    }
    public void notify(String msg){
    }

    @Override
    public void setShips() {
        while (!this.board.allShipsPlaced()) {
            Ship ship = board.getNextShip();
            boolean success = false;
            while (!success) {
                Coordinates start = getCoordinates();
                Coordinates end = getCoordinates();
                success = this.board.placeShip(ship,start, end);
                if (success){
                    this.board.removeShip();
                }
            }
        }
    }

    public void makeAttack(){
        boolean success = false;
        while (!success){
            success = this.bsg.attack(this.board, this.enemyboard, new Coordinates (random.nextInt(10),random.nextInt(10)));
        }
        enemyboard.getOwner().makeAttack();
    }

    public void showBoard(){};
}
*/