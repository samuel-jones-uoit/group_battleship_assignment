package battleship;

/**
 *   Class Name: Destroyer
 *   Class Description:
 *   A type of ship used in the game
 */
public class Destroyer extends Ship{
    public static final int destroyerSize = 2;
    /**
     *   Method Name: Constructor
     *   Method Parameters: None
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public Destroyer(){
        this.size = destroyerSize;
        this.parts = new ShipPart[destroyerSize];
        this.name = "Destroyer";
    }
}
