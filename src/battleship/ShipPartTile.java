package battleship;

public class ShipPartTile extends BoardTile{
    private String hitSymbol;
    private ShipPart part;
    public ShipPartTile(String shipSymbol, String hitSymbol, ShipPart part){
        this.symbol = shipSymbol;
        this.hitSymbol = hitSymbol;
        this.part = part;
    }

    public ShipPart getShipPart(){
        return this.part;
    }
    public void hit(){
        this.symbol = this.hitSymbol;
        this.part.hit();
    }

    public boolean isHit(){
        return this.part.isHit();
    }
}
