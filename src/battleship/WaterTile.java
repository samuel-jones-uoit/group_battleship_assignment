package battleship;

public class WaterTile extends BoardTile{
    private static final String missSymbol = "miss";
    private static final String waterSymbol = "water";
    public WaterTile(){
        this.symbol = waterSymbol;
    }

    // This one is for display only (cannot be hit and doesn't need the symbol)
    public WaterTile(String waterSymbol){
        this.symbol = waterSymbol;
    }

    public void hit(){
        this.symbol = missSymbol;
    }
    public boolean isHit(){
        return this.symbol.equals(missSymbol);
    }
}
