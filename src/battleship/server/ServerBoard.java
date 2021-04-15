package battleship.server;

import battleship.*;

/**
 *   Class Name: ServerGame
 *   Class Description:
 *   BattleShip Board (server perspective)
 */
public class ServerBoard {
    private static final int boardSize = 10;
    private final BoardTile[][] board;
    private final RemotePlayer owner;
    private Battleship battleShip;
    private AircraftCarrier aircraftCarrier;
    private Frigate frigate;
    private Submarine submarine;
    private Destroyer destroyer;
    private Ship[] shipsToPlace;

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   RemotePlayer owner:
     *      Player associated with board instance
     *   Method Description:
     *   This method creates a BattleShip board for the player
     *   Method Return: None
     */
    public ServerBoard(RemotePlayer player1) {
        this.owner = player1;
        this.board = fillBoard(); // initialize
        setShips(); // create ships
    }

    /**
     *   Method Name: fillBoard
     *   Method Parameters: None
     *   Method Description:
     *   This method fills the board with water at the start of the game
     *   Method Return: None
     */
    private BoardTile[][] fillBoard() {
        BoardTile[][] board = new BoardTile[ServerBoard.boardSize][ServerBoard.boardSize];
        for (int r = 0; r < ServerBoard.boardSize; r++){
            for (int c = 0; c < ServerBoard.boardSize; c++){
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
    private BoardTile[][] getViewableBoard(RemotePlayer viewer){
        // owner gets full access
        boolean fullAccess = viewer.is(this.owner);
        BoardTile[][] viewableBoard = new BoardTile[boardSize][boardSize];
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
    /**
     *   Method Name: toString
     *   Method Parameters:
     *   RemotePlayer viewer:
     *      Viewer of the board
     *   Method Description:
     *   This method converts a board to string
     *   Method Return: String representation
     */
    public String toString(RemotePlayer viewer){
        StringBuilder result = new StringBuilder();
        BoardTile[][] viewableBoard = getViewableBoard(viewer);
        for (int row = 0; row < boardSize; row++){
            for (int column = 0; column < boardSize; column++){
                result.append(viewableBoard[row][column].getSymbol()).append("-");
            }
            result.append("|");
        }
        return result.toString();
    }

    /**
     *   Method Name: setShips
     *   Method Parameters: None
     *   Method Description:
     *   This method initializes a bunch of ship objects and adds them to the placing queue
     *   Method Return: None
     */
    public void setShips(){
        battleShip = new Battleship();
        frigate = new Frigate();
        submarine = new Submarine();
        destroyer = new Destroyer();
        aircraftCarrier = new AircraftCarrier();
        shipsToPlace = new Ship[5];
        shipsToPlace[0] = battleShip;
        shipsToPlace[1] = frigate;
        shipsToPlace[2] = submarine;
        shipsToPlace[3] = destroyer;
        shipsToPlace[4] = aircraftCarrier;
    }

    /**
     *   Method Name: PlaceShip
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
    public void placeShip(Ship ship, Coordinates end1, Coordinates end2){
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
            this.board[row][column] = new ShipPartTile("null", newPart);
        }
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
    public void hitSpot(Coordinates coordinates, RemotePlayer attacker){
        // get the tile
        BoardTile b = getTile(coordinates);
        b.hit();
        // if its water nothing more to do
        if (b instanceof WaterTile){
            attacker.notify("You missed!");
            return;
        }
        // hit spot and alert users
        ShipPartTile t = (ShipPartTile) b;
        ShipPart p = t.getShipPart();
        Ship parent = p.getParent();
        if (!parent.isAlive()){
            attacker.notify("You hit & sunk " + owner.getName() + "'s " + parent.getName() + "!");
        }else{
            attacker.notify("You hit " + owner.getName() + "'s " + parent.getName() + "!");
        }
    }

    /**
     *   Method Name: isAlive
     *   Method Parameters: None
     *   Method Description:
     *   This method checks if all ships are alive
     *   Method Return: boolean
     */
    public boolean isAlive() {
        return (battleShip.isAlive() || aircraftCarrier.isAlive() || destroyer.isAlive() || frigate.isAlive() || submarine.isAlive());
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
    public BoardTile getTile(Coordinates coordinates){
        return this.board[coordinates.getRow()][coordinates.getColumn()];
    }

    /**
     *   Method Name: getShipsToPlace
     *   Method Parameters: None
     *   Method Description:
     *   This method returns the ships yet to place
     *   Method Return: Ship[]
     */
    public Ship[] getShipsToPlace(){ return this.shipsToPlace; }

}
