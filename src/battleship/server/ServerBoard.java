package battleship.server;

import battleship.*;


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

    public ServerBoard(RemotePlayer player1) {
        this.owner = player1;
        this.board = fillBoard();
        setShips();
    }

    private BoardTile[][] fillBoard() {
        BoardTile[][] board = new BoardTile[ServerBoard.boardSize][ServerBoard.boardSize];
        for (int r = 0; r < ServerBoard.boardSize; r++){
            for (int c = 0; c < ServerBoard.boardSize; c++){
                board[r][c] = new WaterTile();
            }
        }
        return board;
    }

    public BoardTile[][] getViewableBoard(RemotePlayer viewer){
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

    public void placeShip(Ship ship, Coordinates c1, Coordinates c2){
        int minValue;
        int maxValue;
        boolean horizontal = c1.getRow() == c2.getRow();
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
            this.board[row][column] = new ShipPartTile("null", newPart);
        }
    }


    public void hitSpot(Coordinates coordinates, RemotePlayer attacker){
        BoardTile b = getTile(coordinates);
        b.hit();
        if (b instanceof WaterTile){
            return;
        }
        ShipPartTile t = (ShipPartTile) b; // hopefully this works
        ShipPart p = t.getShipPart();
        Ship parent = p.getParent();
        if (!parent.isAlive()){
            attacker.notify("TEXT NOTIFICATION");
            attacker.notify("You hit & sunk " + owner.getName() + "'s " + parent.getName() + "!");
        }else{
            attacker.notify("TEXT NOTIFICATION");
            attacker.notify("You hit " + owner.getName() + "'s " + parent.getName() + "!");
        }
    }

    public boolean isAlive() {
        return (battleShip.isAlive() || aircraftCarrier.isAlive() || destroyer.isAlive() || frigate.isAlive() || submarine.isAlive());
    }

    public BoardTile getTile(Coordinates coordinates){
        return this.board[coordinates.getRow()][coordinates.getColumn()];
    }
    public Ship[] getShipsToPlace(){ return this.shipsToPlace; }

}
