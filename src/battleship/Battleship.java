package battleship;

/**
 *   Class Name: Battleship
 *   Class Description:
 *   A type of ship used in the game
 */
public class Battleship extends Ship {
    public static final int battleshipSize = 4;
    /**
     *   Method Name: Constructor
     *   Method Parameters: None
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public Battleship(){
        this.size = battleshipSize;
        this.parts = new ShipPart[battleshipSize];
        this.name = "Battleship";
    }

}
