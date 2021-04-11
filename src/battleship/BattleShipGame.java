/*package battleship;

import battleship.client.HumanPlayer;

public class BattleShipGame {
    private Player player1;
    private Player player2;
    private String waterSymbol;
    private String hitSymbol;
    private String missSymbol;
    private Board boardP1;
    private Board boardP2;
    private static final int boardSize = 10;
    public BattleShipGame(PlayerSet players){
        this.player1 = players.getPlayer1();
        this.player2 = players.getPlayer2();
        this.waterSymbol = "waterBlocks";
        this.hitSymbol = "hit";
        this.missSymbol = "miss";
    }


    public void begin() {
        boardP1 = new Board(boardSize, player1, waterSymbol, hitSymbol, missSymbol);
        player1.setBoard(boardP1);
        player1.setBsg(this);
        setShipsPositions.setPlayer((HumanPlayer) player1);
        boardP2 = new Board(boardSize, player2, waterSymbol, hitSymbol, missSymbol);
        player2.setBoard(boardP2);
        player2.setBsg(this);
        player1.setEnemyBoard(boardP2);
        player2.setEnemyBoard(boardP1);
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
        player1.showBoard();
        player2.showBoard();
        player1.makeAttack();
    }

    public boolean attack(Board attacker, Board victim, Coordinates attackCoordinates) {
        if (!validateAttack(attackCoordinates, victim.getViewableBoard(victim.getOwner()))){
            attacker.getOwner().notify("Invalid attack!");
            return false;
        }
        victim.hitSpot(attackCoordinates, attacker.getOwner());
        attacker.getOwner().showBoard();
        victim.getOwner().showBoard();
        if (!boardP2.isAlive()){
            player1.notify(player1.getName() + " wins!");
            player2.notify(player1.getName() + " wins!");
            System.out.println("Pretend its done!!");
        }else if (!boardP1.isAlive()){
            player1.notify(player2.getName() + " wins!");
            player2.notify(player2.getName() + " wins!");
            System.out.println("Pretend its done!!");
        }
        return true;
    }

    private boolean validateAttack(Coordinates attackCoordinates, BoardTile[][] victimBoard) {
        BoardTile attackSpot = victimBoard[attackCoordinates.getRow()][attackCoordinates.getColumn()];
        return !(attackSpot.isHit());
    }

}
*/