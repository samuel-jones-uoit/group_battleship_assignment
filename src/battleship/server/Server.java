package battleship.server;

import battleship.Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *   Class Name: Server
 *   Class Description:
 *   Battleship Game Server
 */
public class Server {
    private static boolean running;
    private static final int serverPort = 16789;

    public static void main(String[] args){
        runBattleShipServer();
    }

    /**
     *   Method Name: runBattleShipServer
     *   Method Parameters: None
     *   Method Description:
     *   This method runs the BattleShip Game Server
     *   Method Return: None
     */
    private static void runBattleShipServer() {
        try{
            System.out.println("Server starting up!");
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket p1Socket;
            Socket p2Socket;
            running = true;
            // until this program is stopped
            while(running) {
                p1Socket = serverSocket.accept();
                System.out.println("Player 1 connected");
                p2Socket = serverSocket.accept();
                System.out.println("Player 2 connected");
                System.out.println("---New Game---");
                Connection p1Connection = Connection.create(p1Socket);
                Connection p2Connection = Connection.create(p2Socket);
                Thread handlerThread = new Thread(new GameHandler(p1Connection, p2Connection));
                handlerThread.start();
            }
        }catch (IOException e){
            System.err.println("Failed to run BattleShip Server - Network error");
        }
    }


    /**
     *   Method Name: stop
     *   Method Parameters: None
     *   Method Description:
     *   This method stops the program
     *   Method Return: None
     */
    public static void stop(){ running = false; }
}
