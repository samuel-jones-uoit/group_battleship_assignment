package battleship;

/**
 *   Class Name: BoardTile
 *   Class Description:
 *   An abstract tile on the board
 */
public abstract class BoardTile {
    protected String symbol;
    public abstract void hit();
    /**
     *   Method Name: getSymbol
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: String (symbol)
     */
    public String getSymbol(){
        return this.symbol;
    }
    public abstract boolean isHit();
}
