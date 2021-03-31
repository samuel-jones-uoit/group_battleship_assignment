package battleship;

public class WaterTile extends BoardTile{
    private char missSymbol;
    public WaterTile(char waterSymbol, char missSymbol){
        this.symbol = waterSymbol;
        this.missSymbol = missSymbol;
    }

    // This one is for display only (cannot be hit and doesn't need the symbol)
    public WaterTile(char waterSymbol){
        this.symbol = waterSymbol;
    }

    public void hit(){
        this.symbol = this.missSymbol;
    }
    public boolean isHit(){
        return this.symbol == this.missSymbol;
    }
}