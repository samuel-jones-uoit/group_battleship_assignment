package battleship;

public class BattleShipGame {
    private Player player1;
    private Player player2;
    private String waterSymbol;
    private String shipSymbol;
    private String hitSymbol;
    private String missSymbol;
    private Board boardP1;
    private Board boardP2;
    private static final int boardSize = 10;
    BattleShipGame(PlayerSet players){
        this.player1 = players.getPlayer1();
        this.player2 = players.getPlayer2();
        this.waterSymbol = "water";
//        this.shipSymbol = ';
        this.hitSymbol = "hit";
        this.missSymbol = "miss";
    }


    public void begin() {
        boardP1 = new Board(boardSize, player1, waterSymbol, shipSymbol, hitSymbol, missSymbol);
        player1.setBoard(boardP1);
        player1.setBsg(this);
        setShipsPositions.setPlayer((HumanPlayer) player1);
        System.out.println(missSymbol);
        boardP2 = new Board(boardSize, player2, waterSymbol, shipSymbol, hitSymbol, missSymbol);
        player2.setBoard(boardP2);
        player2.setBsg(this);
        boardP2.setShips();
        boardP1.setShips();
    }

    public void mainGame(){
//        while(boardP1.isAlive() && boardP2.isAlive()){
//            attack(boardP1, boardP2);
//            if (!(boardP2.isAlive())){
//                break;
//            }
//            attack(boardP2, boardP1);
//        }

        if (boardP1.isAlive()){
            System.out.println(player1.getName() + " wins!");
        }else{
            System.out.println(player2.getName() + " wins!");
        }
    }

    private void attack(Board attacker, Board victim) {
        Player owner = attacker.getOwner();
        if (!(owner instanceof BotPlayer)){
            victim.display(owner);
        }
        boolean valid = false;
        Coordinates attackCoordinates = new Coordinates(0,0); // Intellij thinks it needs initialization
        while (!valid) {
            owner.notify("Enter coordinates on your victim's board");
            attackCoordinates = owner.getCoordinates();
            valid = validateAttack(attackCoordinates, victim.getViewableBoard(victim.getOwner()));
            if (!valid){
                System.out.println("Invalid Selection!");
            }
        }
        victim.hitSpot(attackCoordinates, attacker.getOwner());
    }

    private boolean validateAttack(Coordinates attackCoordinates, BoardTile[][] victimBoard) {
        BoardTile attackSpot = victimBoard[attackCoordinates.getRow()][attackCoordinates.getColumn()];
        return !(attackSpot.isHit());
    }

}
