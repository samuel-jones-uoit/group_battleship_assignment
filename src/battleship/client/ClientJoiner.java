package battleship.client;

import battleship.Connection;
import battleship.Player;
import battleship.PlayerSet;

import java.io.IOException;
import java.net.Socket;
/**
 *   Class Name: ClientJoiner
 *   Class Description:
 *   Used to connecting to server
 */
public class ClientJoiner {
    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   String serverAddress:
     *      Address of the server
     *   int serverPort:
     *      Port of the server
     *   Method Description:
     *   Its a constructor
     *   Method Return: None
     */
    public ClientJoiner(String serverAddress, int serverPort){
        this.SERVER_ADDRESS = serverAddress;
        this.SERVER_PORT = serverPort;
    }
    /**
     *   Method Name: join
     *   Method Parameters:
     *   String name:
     *      Name used by the user
     *   Method Description:
     *   This method allows to user to connect to the server
     *   Method Return: JoinInfo (connection and players)
     */
    public JoinInfo join(String name) throws IOException, RejectedJoinException {
        Socket joinSocket = new Socket(this.SERVER_ADDRESS, this.SERVER_PORT);
        Connection joinConnection = Connection.create(joinSocket);
        joinConnection.send(name); // give the server your name
        String response = joinConnection.receive(); // get either Rejected or other player's name
        if (response.equals("Rejected")){
            throw new RejectedJoinException("Failed to join game.");
        }
        Player me = new HumanPlayer(name);
        Player enemy = new EnemyPlayer(response);
        PlayerSet players = new PlayerSet(me, enemy);
        return new JoinInfo(players, joinConnection);
    }

}
