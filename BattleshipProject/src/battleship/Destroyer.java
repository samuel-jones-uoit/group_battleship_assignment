package battleship;

public class Destroyer extends Ship{
    public static final int destroyerSize = 2;
    public Destroyer(){
        this.size = destroyerSize;
        this.parts = new ShipPart[destroyerSize];
        this.name = "Destroyer";
    }
}
