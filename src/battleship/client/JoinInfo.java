package battleship.client;

import battleship.Connection;
import battleship.PlayerSet;

public class JoinInfo {
    private PlayerSet players;
    private Connection connection;
    public JoinInfo(PlayerSet players, Connection c){
        this.players = players;
        this.connection = c;
    }

    public PlayerSet getPlayers(){ return this.players; }
    public Connection getConnection(){ return this.connection; }
}
