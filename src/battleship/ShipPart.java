package battleship;
/**
 *   Class Name: ShipPart
 *   Class Description:
 *   A part of a ship
 */
public class ShipPart {
    private boolean hit;
    private final Ship parent;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   Ship parent:
     *      the ship owner of the part
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public ShipPart(Ship parent){
        this.hit = false;
        this.parent = parent;
    }

    /**
     *   Method Name: getParent
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: Ship
     */
    public Ship getParent(){
        return this.parent;
    }

    /**
     *   Method Name: isHit
     *   Method Parameters: None
     *   Method Description:
     *   checks if the part is hit
     *   Method Return: boolean
     */
    public boolean isHit(){
        return this.hit;
    }

    /**
     *   Method Name: hit
     *   Method Parameters: None
     *   Method Description:
     *   Set hit to true
     *   Method Return: None
     */
    public void hit(){
        this.hit = true;
    }
}
