package battleship;
/**
 *   Class Name: Submarine
 *   Class Description:
 *   A type of ship used in the game
 */
public class Submarine extends Ship{
    public static final int submarineSize = 3;
    /**
     *   Method Name: Constructor
     *   Method Parameters: None
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public Submarine(){
        this.size = submarineSize;
        this.parts = new ShipPart[submarineSize];
        this.name = "Submarine";
    }
}
