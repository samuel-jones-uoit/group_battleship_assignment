package battleship;

/**
 *   Class Name: Frigate
 *   Class Description:
 *   A type of ship used in the game
 */
public class Frigate extends Ship{
    public static final int frigateSize = 3;
    /**
     *   Method Name: Constructor
     *   Method Parameters: None
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public Frigate(){
        this.size = frigateSize;
        this.parts = new ShipPart[frigateSize];
        this.name = "Frigate";
    }
}
