package battleship;

import java.util.Random;
public class BotPlayer extends Player{
    private Random random = new Random();
    public BotPlayer(String name){
        super(name);
    }

    public Coordinates getCoordinates(){
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
                Coordinates start = new Coordinates (random.nextInt(10),random.nextInt(10));
                Coordinates end =  new Coordinates (random.nextInt(10),random.nextInt(10));
                success = this.board.placeShip(ship,start, end);
                if (success){
                    this.board.removeShip();
                }
            }
        }
    }

    public void makeAttack(){
        notify("Click a spot on your opponent's board");
        boolean success = false;
        while (!success){
            success = this.bsg.attack(this.board, this.enemyboard, new Coordinates (random.nextInt(10),random.nextInt(10)));
        }
        enemyboard.getOwner().makeAttack();
    }

    public void showBoard(){};
}
