package battleship;

public abstract class BoardTile {
    protected char symbol;
    public abstract void hit();
    public char getSymbol(){
        return this.symbol;
    }
    public abstract boolean isHit();
}
