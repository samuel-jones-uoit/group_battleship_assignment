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

}
