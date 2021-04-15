package battleship;

/**
 *   Class Name: PlayerSet
 *   Class Description:
 *   A set of player objects
 */
public class PlayerSet {
    private final Player player1;
    private final Player player2;

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   Player p1:
     *      a player
     *   Player p2:
     *      a player
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public PlayerSet(Player p1, Player p2){
        this.player1 = p1;
        this.player2 = p2;
    }

    /**
     *   Method Name: getPlayer1
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: Player
     */
    public Player getPlayer1(){
        return this.player1;
    }

    /**
     *   Method Name: getPlayer2
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: Player
     */
    public Player getPlayer2(){
        return this.player2;
    }
}
