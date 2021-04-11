package battleship.client;

import battleship.Connection;
import battleship.Player;
import battleship.PlayerSet;
import battleship.server.RemotePlayer;

import java.io.IOException;
import java.net.Socket;

public class ClientJoiner {
    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;
    public ClientJoiner(String serverAddress, int serverPort){
        this.SERVER_ADDRESS = serverAddress;
        this.SERVER_PORT = serverPort;
    }

    public JoinInfo join(String name) throws IOException, RejectedJoinException {
        Socket joinSocket = new Socket(this.SERVER_ADDRESS, this.SERVER_PORT);
        Connection joinConnection = Connection.create(joinSocket);
        joinConnection.send(name);
        String response = joinConnection.receive();
        if (response.equals("Rejected")){
            throw new RejectedJoinException("Failed to join game.");
        }
        Player me = new HumanPlayer(name);
        Player enemy = new EnemyPlayer(response);
        PlayerSet players = new PlayerSet(me, enemy);
        return new JoinInfo(players, joinConnection);
    }
}
