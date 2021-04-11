package battleship.client;

import battleship.*;


public class EnemyPlayer extends ClientPlayer {
    public EnemyPlayer(String name){
        super(name);
    }

    public void notify(String msg){
        setShipsPositions.setInstructions(msg);
    }

    public void createBoard(String instructions){
        this.board = new ClientBoard(this);
        String[] instructionsSplit = instructions.split("\\|");
        for (String twoCoordinates : instructionsSplit){
            Ship ship = this.board.getNextShip();
            this.board.removeShip();
            Coordinates[] coords = decipherTwo(twoCoordinates);
            this.board.confirmPlaceShip(ship, coords[0], coords[1]);
        }
    }

    private Coordinates[] decipherTwo(String twoCoordinates){
        Coordinates[] coords = new Coordinates[2];
        String[] coordinatesSplit = twoCoordinates.split("-");
        coords[0] = Coordinates.fromString(coordinatesSplit[0]);
        coords[1] = Coordinates.fromString(coordinatesSplit[1]);
        return coords;
    }
    public void makeAttack(Coordinates attackCoordinates){
        System.out.println("Enemy making attack at " + attackCoordinates.toString() );
        this.battleShipGame.attack(this, attackCoordinates); // always successful
    }

}
