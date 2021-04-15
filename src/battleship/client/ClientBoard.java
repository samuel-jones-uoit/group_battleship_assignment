package battleship.client;

import battleship.*;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 *   Class Name: ClientBoard
 *   Class Description:
 *   BattleShip Player's Board
 */
public class ClientBoard {
    private static final int boardSize = 10; // 1-10, a-j
    private final BoardTile[][] board;
    private final ClientPlayer owner; // Player connected to instance of board
    private final Queue<Ship> shipsToPlace; // For placing ships
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   ClientPlayer owner:
     *      Player associated with board instance
     *   Method Description:
     *   This method creates a BattleShip board for the player
     *   Method Return: None
     */
    public ClientBoard(ClientPlayer owner){
        this.board = fillBoard();
        this.shipsToPlace = new LinkedBlockingQueue<>();
        this.owner = owner;
        setShips();
    }

    /**
     *   Method Name: fillBoard
     *   Method Parameters: None
     *   Method Description:
     *   This method fills the board with water at the start of the game
     *   Method Return: None
     */
    private BoardTile[][] fillBoard() {
        BoardTile[][] board = new BoardTile[boardSize][boardSize];
        for (int r = 0; r < boardSize; r++){
            for (int c = 0; c < boardSize; c++){
                board[r][c] = new WaterTile();
            }
        }
        return board;
    }

    /**
     *   Method Name: getViewableBoard
     *   Method Parameters:
     *   Player viewer:
     *      Player that would like to see the board. Owner: show everything, enemy: show hits/misses
     *   Method Description:
     *   This method creates a 2d array of board tiles to show to the viewer
     *   Method Return: None
     */
    public BoardTile[][] getViewableBoard(Player viewer){
        // owner gets full access
        boolean fullAccess = viewer.is(this.owner);
        BoardTile[][] viewableBoard = new BoardTile[boardSize][boardSize];
        BoardTile currentTile;
        for (int r = 0; r < boardSize; r++){
            for (int c = 0; c < boardSize; c++){
                // check whether to hide the actual tile
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
    /**
     *   Method Name: setShips
     *   Method Parameters: None
     *   Method Description:
     *   This method initializes a bunch of ship objects and adds them to the placing queue
     *   Method Return: None
     */
    public void setShips(){
        Battleship battleShip = new Battleship();
        Frigate frigate = new Frigate();
        Submarine submarine = new Submarine();
        Destroyer destroyer = new Destroyer();
        AircraftCarrier aircraftCarrier = new AircraftCarrier();
        shipsToPlace.add(battleShip);
        shipsToPlace.add(frigate);
        shipsToPlace.add(submarine);
        shipsToPlace.add(destroyer);
        shipsToPlace.add(aircraftCarrier);
    }

    /**
     *   Method Name: placeShip
     *   Method Parameters:
     *   Ship ship:
     *      Ship being placed
     *   Coordinates end1:
     *      First end of the ship
     *   Coordinates end2:
     *      Other end of the ship
     *   Method Description:
     *   This method places a ship on a board
     *   Method Return: boolean (whether the placement was successful)
     */

    public boolean placeShip(Ship ship, Coordinates end1, Coordinates end2){
        // if the ship is not being placed horizontally or vertically
        if (end1.getRow() != end2.getRow() && end1.getColumn() != end2.getColumn()){
            owner.notifyError("Invalid Selection!", "beforeGame");
            return false;
        }
        // One of these two values is 0 the other is the length of the desired ship placement (this works vertical or horizontal)
        int length = Math.abs(end1.getRow() - end2.getRow()) + Math.abs(end1.getColumn() - end2.getColumn()) + 1;
        if (length != ship.getSize()){
            owner.notifyError("Invalid Selection: Invalid length", "beforeGame");
            return false;
        }
        // if the user is attempting to place the ship over another ship
        if (!(freeSpace(end1, end2))){
            owner.notifyError("Invalid Selection: Space occupied", "beforeGame");
            return false;
        }
        // actually places the ship
        confirmPlaceShip(ship, end1, end2);
        return true;
    }
    /**
     *   Method Name: confirmPlaceShip
     *   Method Parameters:
     *   Ship ship:
     *      Ship being placed
     *   Coordinates end1:
     *      First end of the ship
     *   Coordinates end2:
     *      Other end of the ship
     *   Method Description:
     *   This method places a ship on a board
     *   Method Return: None
     */
    public void confirmPlaceShip(Ship ship, Coordinates end1, Coordinates end2){
        // declare variables
        int minValue;
        int maxValue;
        int row = 0;
        int column = 0;
        boolean horizontal = end1.getRow() == end2.getRow();
        // if the ship is horizontal, set up the min and max correspondingly
        if (horizontal){
            minValue = Math.min(end1.getColumn(), end2.getColumn());
            maxValue = Math.max(end1.getColumn(), end2.getColumn());
            row = end1.getRow();
        }else{ // if its vertical
            minValue = Math.min(end1.getRow(), end2.getRow());
            maxValue = Math.max(end1.getRow(), end2.getRow());
            column = end1.getColumn();
        }

        ShipPart[] parts = ship.getParts();
        ShipPart newPart;
        // loop through the board positions
        for (int currIndex = minValue; currIndex <= maxValue; currIndex++){
            if (horizontal){
                column = currIndex;
            }else{
                row = currIndex;
            }
            // create the ship parts
            newPart = new ShipPart(ship);
            parts[currIndex - minValue] = newPart;
            // add a ship part tile containing the ship part to the board
            this.board[row][column] = new ShipPartTile(getSymbolID(ship, currIndex - minValue, horizontal), newPart);
        }

        // Update the board for the user
        if (this.owner instanceof HumanPlayer){
            HumanPlayer t = (HumanPlayer) owner;
            t.showBoardBeforeGame();
        }
    }
    /**
     *   Method Name: freeSpace
     *   Method Parameters:
     *   Coordinates c1:
     *      Position 1 on the board
     *   Coordinates c2:
     *      Position 2 on the board
     *   Method Description:
     *   This method checks some tiles on a grid to see if a ship can be placed on them
     *   Method Return: boolean --> if free then true else false
     */
    private boolean freeSpace(Coordinates c1, Coordinates c2){
        int minValue;
        int maxValue;
        int row = 0;
        int column = 0;
        boolean horizontal = c1.getRow() == c2.getRow();
        // if the ship is being placed horizontally
        if (horizontal){
            minValue = Math.min(c1.getColumn(), c2.getColumn());
            maxValue = Math.max(c1.getColumn(), c2.getColumn());
            row = c1.getRow();
        }else{ // else if the ship will be vertical
            minValue = Math.min(c1.getRow(), c2.getRow());
            maxValue = Math.max(c1.getRow(), c2.getRow());
            column = c1.getColumn();
        }
        // loop through the board positions
        for (int currIndex = minValue; currIndex <= maxValue; currIndex++){
            if (horizontal){
                column = currIndex;
            }else{
                row = currIndex;
            }
            // if the spot is taken --> return not free
            if (this.board[row][column] instanceof ShipPartTile){
                return false;
            }
        }
        return true;
    }
    /**
     *   Method Name: hitSpot
     *   Method Parameters:
     *   Coordinates coordinates:
     *      Position on the board
     *   Method Description:
     *   This method hits a spot on the board
     *   Method Return: None
     */
    public void hitSpot(Coordinates coordinates){
        // get the tile
        BoardTile b = getTile(coordinates);
        b.hit();
        // if its water nothing more to do
        if (b instanceof WaterTile){
            return;
        }
        // hit spot and alert users
        ShipPartTile t = (ShipPartTile) b;
        ShipPart p = t.getShipPart();
        Ship parent = p.getParent();
        if (!parent.isAlive()){
            owner.notify("Your " + parent.getName() + " was hit & sunk!");
        }else{
            owner.notify("Your " + parent.getName() + " was hit!");
        }


    }
    /**
     *   Method Name: getTile
     *   Method Parameters:
     *   Coordinates coordinates:
     *      Get the tile at coordinates
     *   Method Description:
     *   This method checks some tiles on a grid to see if a ship can be placed on them
     *   Method Return: boolean --> if free then true else false
     */
    private BoardTile getTile(Coordinates coordinates){
        return this.board[coordinates.getRow()][coordinates.getColumn()];
    }

    /**
     *   Method Name: getNextShip
     *   Method Parameters: None
     *   Method Description:
     *   This method returns the ship at the head of the queue
     *   Method Return: Ship
     */
    public Ship getNextShip(){
        return shipsToPlace.peek();

    }

    /**
     *   Method Name: removeShip
     *   Method Parameters: None
     *   Method Description:
     *   This method removes the ship at the head of the queue
     *   Method Return: None
     */
    public void removeShip(){
        shipsToPlace.remove();
    }

    /**
     *   Method Name: allShipsPlaced
     *   Method Parameters: None
     *   Method Description:
     *   Coordinates coordinates:
     *      Get the tile at coordinates
     *   This method is used to determine if all ships have been placed
     *   Method Return: Ship
     */
    public boolean allShipsPlaced() {
        return shipsToPlace.isEmpty();
    }

    /**
     *   Method Name: getSymbolID
     *   Method Parameters:
     *   Ship ship:
     *      Some type of ship
     *   int index:
     *      Position in the ship example for frigate: 0 --> head 1 --> middle 2 --> end
     *   boolean horizontal:
     *      is the ship placed horizontally
     *   Method Description:
     *   This method is used to determine if all ships have been placed
     *   Method Return: String (symbol)
     */
    private String getSymbolID(Ship ship, int index, boolean horizontal){
        // if this is not an end of the ship its a middle symbol
        if (index != 0 && index != ship.getSize() -1){
            return "middle";
        }

        // if its the 0 position its either left or up
        if (index == 0){
            if (horizontal){
                return "left";
            }
            return "up";
        }

        // it must be the other end so right or down
        if (horizontal) {
            return "right";
        }

        return "down";
    }

    /**
     *   Method Name: fromString
     *   Method Parameters:
     *   String board:
     *      String representation of a string
     *   Method Description:
     *   This method is used to get all the symbols from a board which is represented by a string
     *   Method Return: String[][] symbols
     */
    private static String[][] fromString(String board){
        String[][] symbols = new String[boardSize][boardSize];
        String[] rows = board.split("\\|");
        // loop through and copy the symbols from a string to 2d string array
        for (int row = 0; row < rows.length; row++){
            String[] columns = rows[row].split("-");
            for (int column = 0; column < columns.length; column++){
                symbols[row][column] = columns[column];
            }
        }
        return symbols;
    }

    /**
     *   Method Name: getBoardSize
     *   Method Parameters: None
     *   Method Description:
     *   This method is used to obtain size of a board
     *   Method Return: int boardSize
     */
    public int getBoardSize(){
        return boardSize;
    }

    /**
     *   Method Name: setTitle
     *   Method Parameters:
     *   int row:
     *      index of row
     *   int column:
     *      index of column
     *   BoardTile tile:
     *      Tile to replace value with
     *   Method Description:
     *   This method is used to set a tile on the board
     *   Method Return: None
     */
    public void setTile(int row, int column, BoardTile tile){
        this.board[row][column] = tile;
    }

    /**
     *   Method Name: makeBoard
     *   Method Parameters:
     *   String s:
     *      String representation of a board
     *   EnemyPlayer owner:
     *      owner of the board
     *   Method Description:
     *   This method is used to set a tile on the board
     *   Method Return: ClientBoard (enemyBoard)
     */
    public static ClientBoard makeBoard(String s, EnemyPlayer owner){
        // get the symbols
        String[][] symbols = fromString(s);
        ClientBoard newBoard = new ClientBoard(owner);
        // loop through the board positions
        for (int row = 0; row < boardSize; row++){
            for (int column = 0; column < boardSize; column++){
                // newBoard initialized to water no need to set
                if (symbols[row][column].equals("water")){ continue; }
                // hit the water
                if (symbols[row][column].equals("miss")){ newBoard.hitSpot(new Coordinates(row, column)); continue; }
                // else its a hit
                ShipPartTile shipPartTile;
                shipPartTile = new ShipPartTile(symbols[row][column], new ShipPart(new UnknownShip()));
                shipPartTile.hit();
                newBoard.setTile(row, column, shipPartTile);
            }
        }
        return newBoard;
    }
    /**
     *   Method Name: outOfShips
     *   Method Parameters: None
     *   Method Description:
     *   This method is used to determine if the ship queue is empty
     *   Method Return: boolean empty --> true else false
     */
    public boolean outOfShips(){ return shipsToPlace.isEmpty(); }
}
