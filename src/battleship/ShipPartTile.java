package battleship;

/**
 *   Class Name: ShipPartTile
 *   Class Description:
 *   BoardTile for ShipPart
 */
public class ShipPartTile extends BoardTile{
    private static final String hitSymbol = "hit";
    private final ShipPart part;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   String shipSymbol:
     *      Symbol
     *   ShipPart part:
     *      Ship part
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public ShipPartTile(String shipSymbol, ShipPart part){
        this.symbol = shipSymbol;
        this.part = part;
    }

    /**
     *   Method Name: getShipPart
     *   Method Parameters: None
     *   Getter
     *   Method Return: ShipPart
     */
    public ShipPart getShipPart(){
        return this.part;
    }

    /**
     *   Method Name: hit
     *   Method Parameters: None
     *   Hit a part
     *   Method Return: None
     */
    public void hit(){
        this.symbol = hitSymbol;
        this.part.hit();
    }

    /**
     *   Method Name: isHit
     *   Method Parameters: None
     *   Check if a part is hit
     *   Method Return: boolean
     */
    public boolean isHit(){
        return this.part.isHit();
    }
}
