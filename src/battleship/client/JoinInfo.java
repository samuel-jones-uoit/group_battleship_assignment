package battleship.client;

import battleship.Connection;
import battleship.PlayerSet;
/**
 *   Class Name: JoinInfo
 *   Class Description:
 *   Stores info created when joining game
 */
public class JoinInfo {
    private final PlayerSet players;
    private final Connection connection;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   PlayerSet players:
     *      Players in the game
     *   Connection c:
     *      Connection used in the game
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public JoinInfo(PlayerSet players, Connection c){
        this.players = players;
        this.connection = c;
    }

    /**
     *   Method Name: getPlayers
     *   Method Parameters:
     *   Method Description:
     *   Getter
     *   Method Return: PlayerSet players
     */
    public PlayerSet getPlayers(){ return this.players; }
    /**
     *   Method Name: getConnection
     *   Method Parameters:
     *   Method Description:
     *   Getter
     *   Method Return: Connection connection
     */
    public Connection getConnection(){ return this.connection; }
}
