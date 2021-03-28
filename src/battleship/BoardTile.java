package battleship;

public abstract class BoardTile {
    private char symbol;
    private Coordinates coordinates;
    public BoardTile(char symbol, Coordinates coordinates){
        this.symbol = symbol;
        this.coordinates = coordinates;
    }

    public char getSymbol(){
        return this.symbol;
    }
}
