package battleship;

/**
 *   Class Name: Ship
 *   Class Description:
 *   An abstract ship class
 */
public abstract class Ship {
    protected int size;
    protected ShipPart[] parts;
    protected String name;

    /**
     *   Method Name: getSize
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: int
     */
    public int getSize(){
        return this.size;
    }

    /**
     *   Method Name: getName
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: String
     */
    public String getName(){ return this.name; }

    /**
     *   Method Name: getParts
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: ShipPart[]
     */
    public ShipPart[] getParts(){
        return this.parts;
    }

    /**
     *   Method Name: isAlive
     *   Method Parameters: None
     *   Method Description:
     *   Check if a ship is alive
     *   Method Return: boolean
     */
    public boolean isAlive(){
        for (ShipPart part : this.parts){
            if (!(part.isHit())){
                return true;
            }
        }
        return false;
    }
}
