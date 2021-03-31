package battleship;

import java.util.ArrayList;

public class Board {
    private int boardSize;
    private BoardTile[][] board;
    private char waterSymbol;
    private char shipSymbol;
    private char hitSymbol;
    private char missSymbol;
    private Player owner;
    private Battleship battleShip;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private Submarine submarine;
    private Destroyer destroyer;
    private ArrayList<Ship> shipList;
    Board(int boardSize, Player owner, char waterSymbol, char shipSymbol, char hitSymbol, char missSymbol){
        this.missSymbol = missSymbol;
        this.boardSize = boardSize;
        this.waterSymbol = waterSymbol;
        this.shipSymbol = shipSymbol;
        this.hitSymbol = hitSymbol;
        this.board = fillBoard(boardSize);
        this.shipList = new ArrayList<>();
        this.owner = owner;
    }

    private BoardTile[][] fillBoard(int n) {
        BoardTile[][] board = new BoardTile[n][n];
        for (int r = 0; r < n; r++){
            for (int c = 0; c < n; c++){
                board[r][c] = new WaterTile(this.waterSymbol, this.missSymbol);
            }
        }
        return board;
    }
    public void display(Player viewer){
        BoardTile[][] accessibleBoard = this.getViewableBoard(viewer);
        String line;
        System.out.println(this.owner.getName() + "'s board (" + viewer.getName() + "'s view)");
        for (int r = 0; r < this.boardSize; r++){
            line = "";
            line += Coordinates.convertRowToChar(r);
            for (int c = 0; c < this.boardSize; c++){
                line += " ";
                line += accessibleBoard[r][c].getSymbol();
            }
            System.out.println(line);
        }
        line = " ";
        for (int c = 0; c < this.boardSize; c++){
            line += " ";
            line += Integer.toString(Coordinates.convertCIndex(c));
        }
        System.out.println(line);
    }

    public BoardTile[][] getViewableBoard(Player viewer){
        boolean fullAccess = viewer.is(this.owner);
        BoardTile[][] viewableBoard = new BoardTile[this.boardSize][this.boardSize];
        BoardTile currentTile;
        for (int r = 0; r < boardSize; r++){
            for (int c = 0; c < boardSize; c++){
                if (!fullAccess && this.board[r][c] instanceof ShipPartTile && !((ShipPartTile) this.board[r][c]).getShipPart().isHit()){
                    viewableBoard[r][c] = new WaterTile(this.waterSymbol);
                    continue;
                }
                currentTile = this.board[r][c];
                viewableBoard[r][c] = currentTile;
            }
        }
        return viewableBoard;
    }

    public void setShips(){
        battleShip = new Battleship();
        frigate = new Frigate();
        submarine = new Submarine();
        destroyer = new Destroyer();
        aircraftCarrier = new AircraftCarrier();
        shipList.add(battleShip);
        shipList.add(frigate);
        shipList.add(submarine);
        shipList.add(destroyer);
        shipList.add(aircraftCarrier);
        placeShip(battleShip);
        placeShip(frigate);
        placeShip(submarine);
        placeShip(destroyer);
        placeShip(aircraftCarrier);
        // temp
        if (!(this.owner instanceof BotPlayer)){
            this.display(this.owner);
        }
    }

    private void placeShip(Ship ship){
        Coordinates end1;
        Coordinates end2;
        // temp
        if (!(this.owner instanceof BotPlayer)){
            this.display(this.owner);
        }
        // loop until its set up
        while (true){
            owner.notify("Please enter the row and column ([a-j],[1-10]) of one end of your " + ship.getName() + ":");
            end1 = owner.getCoordinates();
            owner.notify("Please enter the row and column ([a-j],[1-10]) of the other end of your " + ship.getName() + ":");
            end2 = owner.getCoordinates();
            if (end1.getRow() != end2.getRow() && end1.getColumn() != end2.getColumn()){
                owner.notify("Invalid Selection!");
                continue;
            }
            // One of these two values is 0 the other is the length of the desired ship placement (this works vertical or horizontal)
            int length = Math.abs(end1.getRow() - end2.getRow()) + Math.abs(end1.getColumn() - end2.getColumn()) + 1;
            if (length != ship.getSize()){
                owner.notify("Invalid Selection: invalid length");
                continue;
            }
            if (!(freeSpace(end1, end2))){
                owner.notify("Invalid Selection: Space occupied");
                continue;
            }
            placeShip(ship, end1, end2);
            break;
        }

    }

    private void placeShip(Ship ship, Coordinates c1, Coordinates c2){
        int minValue;
        int maxValue;
        boolean horizontal = c1.getRow() == c2.getRow();
        // initialization not necessary but IntelliJ can't figure that out
        int row = 0;
        int column = 0;
        if (horizontal){
            minValue = Math.min(c1.getColumn(), c2.getColumn());
            maxValue = Math.max(c1.getColumn(), c2.getColumn());
            row = c1.getRow();
        }else{
            minValue = Math.min(c1.getRow(), c2.getRow());
            maxValue = Math.max(c1.getRow(), c2.getRow());
            column = c1.getColumn();
        }
        ShipPart[] parts = ship.getParts();
        ShipPart newPart;
        for (int currIndex = minValue; currIndex <= maxValue; currIndex++){
            if (horizontal){
                column = currIndex;
            }else{
                row = currIndex;
            }
            // don't need to check if its been hit because haven't got to that part of the game
            newPart = new ShipPart(ship);
            parts[currIndex - minValue] = newPart;
            this.board[row][column] = new ShipPartTile(this.shipSymbol, this.hitSymbol, newPart);
        }
    }

    private boolean freeSpace(Coordinates c1, Coordinates c2){
        int minValue;
        int maxValue;
        boolean horizontal = c1.getRow() == c2.getRow();
        // initialization not necessary but IntelliJ can't figure that out
        int row = 0;
        int column = 0;
        if (horizontal){
            minValue = Math.min(c1.getColumn(), c2.getColumn());
            maxValue = Math.max(c1.getColumn(), c2.getColumn());
            row = c1.getRow();
        }else{
            minValue = Math.min(c1.getRow(), c2.getRow());
            maxValue = Math.max(c1.getRow(), c2.getRow());
            column = c1.getColumn();
        }

        for (int currIndex = minValue; currIndex <= maxValue; currIndex++){
            if (horizontal){
                column = currIndex;
            }else{
                row = currIndex;
            }
            // don't need to check if its been hit because haven't got to that part of the game
            if (this.board[row][column] instanceof ShipPartTile){
                return false;
            }
        }
        return true;
    }
    public ArrayList<Ship> getShips(){
        return this.shipList;
    }

    public ArrayList<Ship> getLivingShips(){
        ArrayList<Ship> livingShips = new ArrayList<>();
        for (Ship s : this.getShips()){
            if (s.isAlive()){
                livingShips.add(s);
            }
        }
        return livingShips;
    }

    public void hitSpot(Coordinates coordinates, Player attacker){
        BoardTile b = getTile(coordinates);
        b.hit();
        if (b instanceof WaterTile){
            return;
        }
        ShipPartTile t = (ShipPartTile) b; // hopefully this works
        ShipPart p = t.getShipPart();
        Ship parent = p.getParent();
        owner.notify("Your " + parent.getName() + " was hit!");
        attacker.notify("You hit " + owner.getName() + "'s " + parent.getName() + "!");
        if (!parent.isAlive()){
            owner.notify("Your " + parent.getName() + " was sunk!");
            attacker.notify("You sunk " + owner.getName() + "'s " + parent.getName() + "!");
        }

    }

    public boolean isAlive() {
        return (battleShip.isAlive() || aircraftCarrier.isAlive() || destroyer.isAlive() || frigate.isAlive() || submarine.isAlive());
    }

    public Player getOwner(){
        return this.owner;
    }

    public BoardTile getTile(Coordinates coordinates){
        return this.board[coordinates.getRow()][coordinates.getColumn()];
    }
}
