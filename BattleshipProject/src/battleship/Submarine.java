package battleship;

public class Submarine extends Ship{
    public static final int submarineSize = 3;
    public Submarine(){
        this.size = submarineSize;
        this.parts = new ShipPart[submarineSize];
        this.name = "Submarine";
    }
}
