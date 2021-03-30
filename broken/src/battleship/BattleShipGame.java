package battleship;

public class BattleShipGame {
    private Player player1;
    private Player player2;
    private char waterSymbol;
    private char shipSymbol;
    private char hitSymbol;
    private Board boardP1;
    private Board boardP2;
    private static final int boardSize = 10;
    BattleShipGame(PlayerSet players){
        this.player1 = players.getPlayer1();
        this.player2 = players.getPlayer2();
        this.waterSymbol = '~';
        this.shipSymbol = '*';
        this.hitSymbol = 'x';
    }


    public void start() {
        boardP1 = new Board(boardSize, player1, waterSymbol, shipSymbol, hitSymbol);
        boardP2 = new Board(boardSize, player2, waterSymbol, shipSymbol, hitSymbol);
        boardP1.Display(player1);
    }

}
