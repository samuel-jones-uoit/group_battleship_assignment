package battleship;

public class Frigate extends Ship{
    public static final int frigateSize = 3;
    public Frigate(){
        this.size = frigateSize;
        this.parts = new ShipPart[frigateSize];
        this.name = "Frigate";
    }
}
