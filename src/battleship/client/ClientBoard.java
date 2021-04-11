package battleship.client;

import battleship.*;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientBoard {
    private final int boardSize = 10;
    private BoardTile[][] board;
    private ClientPlayer owner;
    private Battleship battleShip;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private Submarine submarine;
    private Destroyer destroyer;
    private final Queue<Ship> shipsToPlace;
    public ClientBoard(ClientPlayer owner){
        this.board = fillBoard();
        this.shipsToPlace = new LinkedBlockingQueue<>();
        this.owner = owner;
        setShips();
    }

    private BoardTile[][] fillBoard() {
        BoardTile[][] board = new BoardTile[boardSize][boardSize];
        for (int r = 0; r < boardSize; r++){
            for (int c = 0; c < boardSize; c++){
                board[r][c] = new WaterTile();
            }
        }
        return board;
    }


    public BoardTile[][] getViewableBoard(Player viewer){
        boolean fullAccess = viewer.is(this.owner);
        BoardTile[][] viewableBoard = new BoardTile[this.boardSize][this.boardSize];
        BoardTile currentTile;
        for (int r = 0; r < boardSize; r++){
            for (int c = 0; c < boardSize; c++){
                if (!fullAccess && this.board[r][c] instanceof ShipPartTile && !((ShipPartTile) this.board[r][c]).getShipPart().isHit()){
                    viewableBoard[r][c] = new WaterTile();
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
            this.board[row][column] = new ShipPartTile(getSymbolID(ship, currIndex - minValue, horizontal), newPart);
        }
        if (this.owner instanceof HumanPlayer){
            HumanPlayer t = (HumanPlayer) owner;
            t.showBoardBeforeGame();
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

    public void hitSpot(Coordinates coordinates){
        BoardTile b = getTile(coordinates);
        b.hit();
        if (b instanceof WaterTile){
            return;
        }
        ShipPartTile t = (ShipPartTile) b; // hopefully this works
        ShipPart p = t.getShipPart();
        Ship parent = p.getParent();
        if (!parent.isAlive()){
            owner.notify("Your " + parent.getName() + " was hit & sunk!");
        }else{
            owner.notify("Your " + parent.getName() + " was hit!");
        }


    }

    public boolean isAlive() {
        return (battleShip.isAlive() || aircraftCarrier.isAlive() || destroyer.isAlive() || frigate.isAlive() || submarine.isAlive());
    }

    public BoardTile getTile(Coordinates coordinates){
        return this.board[coordinates.getRow()][coordinates.getColumn()];
    }
    public Ship getNextShip(){
        return shipsToPlace.peek();
    }
    public void removeShip(){
        shipsToPlace.remove();
    }

    public boolean allShipsPlaced(){
        return shipsToPlace.isEmpty();
    }

    private String getSymbolID(Ship ship, int index, boolean horiz){
        if (index != 0 && index != ship.getSize() -1){
            return "middle";
        }

        if (index == 0){
            if (horiz){
                return "left";
            }
            return "up";
        }

        if (horiz) {
            return "right";
        }

        return "down";
    }

    public int getBoardSize(){
        return this.boardSize;
    }
}