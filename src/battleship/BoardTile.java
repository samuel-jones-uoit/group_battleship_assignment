package battleship;

public abstract class BoardTile {
    protected String symbol;
    public abstract void hit();
    public String getSymbol(){
        return this.symbol;
    }
    public abstract boolean isHit();
}
