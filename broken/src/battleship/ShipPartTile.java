package battleship;

public class ShipPartTile extends BoardTile{
    private ShipPart part;
    public ShipPartTile(char symbol, Coordinates coordinates, ShipPart part){
        super(symbol, coordinates);
        this.part = part;
    }
}
