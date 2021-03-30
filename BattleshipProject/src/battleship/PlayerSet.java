package battleship;

public class PlayerSet {
    private Player player1;
    private Player player2;

    public PlayerSet(Player p1, Player p2){
        this.player1 = p1;
        this.player2 = p2;
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }
}
