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
        return;
    }

    @Override
    public void setShips() {
        while (!this.board.allShipsPlaced()) {
            Ship ship = board.getNextShip();
            boolean success = false;
            while (success != true) {
                Coordinates start = new Coordinates (random.nextInt(10),random.nextInt(10));
                Coordinates end =  new Coordinates (random.nextInt(10),random.nextInt(10));
                success = this.board.placeShip(ship,start, end);
            }
        }
    }
}
