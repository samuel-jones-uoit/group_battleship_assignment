package battleship;

public class Board {
    private int boardSize;
    private BoardTile[][] board;
    private char waterSymbol;
    private char shipSymbol;
    private char hitSymbol;
    private Player owner;
    private Battleship battleShip;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private Submarine submarine;
    private Destroyer destroyer;

    Board(int n, Player owner, char waterSymbol, char shipSymbol, char hitSymbol){
        this.boardSize = n;
        this.waterSymbol = waterSymbol;
        this.shipSymbol = shipSymbol;
        this.hitSymbol = hitSymbol;
        this.board = fillBoard(n);
        this.owner = owner;
    }

    private BoardTile[][] fillBoard(int n) {
        BoardTile[][] board = new BoardTile[n][n];
        for (int r = 0; r < n; r++){
            for (int c = 0; c < n; c++){
                board[r][c] = new WaterTile(this.waterSymbol, new Coordinates(r,c));
            }
        }
        return board;
    }

    public void Display(Player viewer){
        boolean fullAccess = viewer.is(this.owner);
        BoardTile currentTile;
        String line;
        for (int r = 0; r < boardSize; r++){
            line = "";
            line += Coordinates.convertRowToChar(r);
            for (int c = 0; c < boardSize; c++){
                line += " ";
                if (!fullAccess){
                    line += waterSymbol;
                    continue;
                }
                currentTile = this.board[r][c];
                line += currentTile.getSymbol();
            }
            System.out.println(line);
        }
        line = " ";
        for (int c = 0; c < boardSize; c++){
            line += " ";
            line += Integer.toString(Coordinates.convertCIndex(c));
        }
        System.out.println(line);
    }

    public void setShips(){
        battleShip = new Battleship(Battleship.battleshipSize);
        frigate = new Frigate(Frigate.frigateSize);
        submarine = new Submarine(Submarine.submarineSize);
        destroyer = new Destroyer(Destroyer.destroyerSize);
        aircraftCarrier = new AircraftCarrier(AircraftCarrier.aircraftCarrierSize);
        placeShip(battleShip);
    }

    private void placeShip(Ship ship){
        Coordinates end1;
        Coordinates end2;

        // loop until its set up
        while (true){
            owner.notify("Please enter the row and column ([a-j],[1-10]) of one end of your ship:");
            end1 = owner.getCoordinates();
            if ()
        }

    }
}
