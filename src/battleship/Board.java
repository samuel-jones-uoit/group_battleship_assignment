package battleship;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Board {
    private int boardSize;
    private BoardTile[][] board;
    private String waterSymbol;
    private String shipSymbol;
    private String hitSymbol;
    private String missSymbol;
    private Player owner;
    private Battleship battleShip;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private Submarine submarine;
    private Destroyer destroyer;
    private Queue<Ship> shipsToPlace;
    Board(int boardSize, Player owner, String waterSymbol, String shipSymbol, String hitSymbol, String missSymbol){
        this.missSymbol = missSymbol;
        this.boardSize = boardSize;
        this.waterSymbol = waterSymbol;
        this.shipSymbol = shipSymbol;
        this.hitSymbol = hitSymbol;
        this.board = fillBoard(boardSize);
        this.shipsToPlace = new LinkedBlockingQueue<>();

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
        owner.notify(this.owner.getName() + "'s board (" + viewer.getName() + "'s view)");
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
        shipsToPlace.add(battleShip);
        shipsToPlace.add(frigate);
        shipsToPlace.add(submarine);
        shipsToPlace.add(destroyer);
        shipsToPlace.add(aircraftCarrier);
        owner.setShips();
    }

    public boolean placeShip(Ship ship, Coordinates end1, Coordinates end2){
        if (end1.getRow() != end2.getRow() && end1.getColumn() != end2.getColumn()){
            owner.notify("Invalid Selection!");
            return false;
        }
        // One of these two values is 0 the other is the length of the desired ship placement (this works vertical or horizontal)
        int length = Math.abs(end1.getRow() - end2.getRow()) + Math.abs(end1.getColumn() - end2.getColumn()) + 1;
        if (length != ship.getSize()){
            owner.notify("Invalid Selection: invalid length");
            return false;
        }
        if (!(freeSpace(end1, end2))){
            owner.notify("Invalid Selection: Space occupied");
            return false;
        }
        confirmPlaceShip(ship, end1, end2);
        return true;
    }

    public void confirmPlaceShip(Ship ship, Coordinates c1, Coordinates c2){
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
        if(owner instanceof HumanPlayer) {
            changeShipImages(c1, c2);
        }
    }


    //After Placing Ships Change Tile Pics
    private static void changeShipImages(Coordinates start, Coordinates end){
        if(start.getColumn() == end.getColumn()){
            int length = Math.abs(start.getRow() - end.getRow());
            System.out.println(length);
            if(start.getRow() > end.getRow()){
                setShipsPositions.btns[start.getRow()][start.getColumn()].setId("right");
                setShipsPositions.btns[start.getRow()][start.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                setShipsPositions.btns[end.getRow()][end.getColumn()].setId("left");
                setShipsPositions.btns[end.getRow()][end.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                if (length>1){
                    for(int i = (start.getRow()-(length-1)); i<(end.getRow()+ length); i++){
                        setShipsPositions.btns[i][end.getColumn()].setId("middle");
                        setShipsPositions.btns[i][end.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                    }
                }
            }
            else if(start.getRow() < end.getRow()){
                setShipsPositions.btns[start.getRow()][start.getColumn()].setId("left");
                setShipsPositions.btns[start.getRow()][start.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                setShipsPositions.btns[end.getRow()][end.getColumn()].setId("right");
                setShipsPositions.btns[end.getRow()][end.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                if (length>1){
                    for(int i = (end.getRow()+-(length-1)); i<(start.getRow() + length); i++){
                        setShipsPositions.btns[i][end.getColumn()].setId("middle");
                        setShipsPositions.btns[i][end.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                    }
                }
            }
        }

        else if(end.getRow() == start.getRow()){
            int length = Math.abs(end.getColumn() - start.getColumn());
            System.out.println(length);
            if(start.getColumn() > end.getColumn()){
                setShipsPositions.btns[start.getRow()][start.getColumn()].setId("down");
                setShipsPositions.btns[start.getRow()][start.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                setShipsPositions.btns[end.getRow()][end.getColumn()].setId("up");
                setShipsPositions.btns[end.getRow()][end.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                if (length>1){
                    for(int i = (start.getColumn()-(length-1)); i<(end.getColumn()+ length); i++){
                        setShipsPositions.btns[end.getRow()][i].setId("middle");
                        setShipsPositions.btns[end.getRow()][i].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                    }
                }
            }
            else if(start.getColumn() < end.getColumn()){
                setShipsPositions.btns[start.getRow()][start.getColumn()].setId("up");
                setShipsPositions.btns[start.getRow()][start.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                setShipsPositions.btns[end.getRow()][end.getColumn()].setId("down");
                setShipsPositions.btns[end.getRow()][end.getColumn()].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                if (length>1){
                    for(int i = (end.getColumn()+-(length-1)); i<(start.getColumn() + length); i++){
                        setShipsPositions.btns[end.getRow()][i].setId("middle");
                        setShipsPositions.btns[end.getRow()][i].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
                    }
                }
            }
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
    public Ship getNextShip(){
        return shipsToPlace.remove();
    }

    public boolean allShipsPlaced(){
        return shipsToPlace.isEmpty();
    }
}
