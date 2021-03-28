package battleship;

public class BotPlayer extends Player{
    public BotPlayer(String name){
        super(name);
    }

    public Coordinates getCoordinates(){
        return new Coordinates(0,0);
    }
    public void notify(String msg){
        return;
    }
}
