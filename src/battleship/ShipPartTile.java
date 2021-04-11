package battleship;

public class ShipPartTile extends BoardTile{
    private static final String hitSymbol = "hit";
    private ShipPart part;
    public ShipPartTile(String shipSymbol, ShipPart part){
        this.symbol = shipSymbol;
        this.part = part;
    }

    public ShipPart getShipPart(){
        return this.part;
    }
    public void hit(){
        this.symbol = hitSymbol;
        this.part.hit();
    }

    public boolean isHit(){
        return this.part.isHit();
    }
}
