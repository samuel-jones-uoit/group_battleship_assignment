package battleship;

/**
 *   Class Name: WaterTile
 *   Class Description:
 *   A type of BoardTile
 */
public class WaterTile extends BoardTile{
    private static final String missSymbol = "miss";
    private static final String waterSymbol = "water";
    /**
     *   Method Name: Constructor
     *   Method Parameters: None
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public WaterTile(){
        this.symbol = waterSymbol;
    }

    /**
     *   Method Name: hit
     *   Method Parameters: None
     *   Method Description:
     *   Hit a spot
     *   Method Return: None
     */
    public void hit(){
        this.symbol = missSymbol;
    }

    /**
     *   Method Name: isHit
     *   Method Parameters: None
     *   Method Description:
     *   Check if hit
     *   Method Return: boolean
     */
    public boolean isHit(){
        return this.symbol.equals(missSymbol);
    }
}
