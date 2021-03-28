package battleship;

public class Battleship extends Ship {
    public static final int battleshipSize = 4;

    public Battleship(){
        this.size = battleshipSize;
        this.parts = new ShipPart[battleshipSize];
        this.name = "Battleship";
    }

}
