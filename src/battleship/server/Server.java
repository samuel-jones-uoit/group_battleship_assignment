package battleship.server;

import battleship.Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *   Class Name: Server
 *   Class Description:
 *   File Share Server
 */
public class Server {
    private static boolean running;
    private static final int serverPort = 16789;

    public static void main(String[] args){
        runBattleShipServer();
    }

    /**
     *   Method Name: runFileShare
     *   Method Parameters: None
     *   Method Description:
     *   This method runs the File Sharing server
     *   Method Return: Connection
     */
    private static void runBattleShipServer() {
        try{
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket p1Socket;
            Socket p2Socket;
            running = true;
            // until this program is stopped
            while(running) {
                p1Socket = serverSocket.accept();
                p2Socket = serverSocket.accept();
                Connection p1Connection = Connection.create(p1Socket);
                Connection p2Connection = Connection.create(p2Socket);
                Thread handlerThread = new Thread(new GameHandler(p1Connection, p2Connection));
                handlerThread.start();
            }
        }catch (IOException e){
            System.err.println("Failed to run file share server.");
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