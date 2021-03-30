package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BattleShipLobby {
    private String host;
    private int port;
    public BattleShipLobby(String host, int port){
        this.host = host;
        this.port = port;
    }

    //public PlayerSet run(){

    //}

    private static Socket getSocket(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        serverSocket.close();
        return clientSocket;
    }
}
