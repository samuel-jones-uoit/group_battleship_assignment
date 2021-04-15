package battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *   Class Name: Connection
 *   Class Description:
 *   Used to facilitate network connections
 */
public class Connection {
    private final Socket socket;
    private final PrintWriter writer; // output
    private final BufferedReader reader; // input

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   Socket socket:
     *      Socket used for connection
     *   PrintWriter writer:
     *      PrintWriter used to send information
     *   BufferedReader reader:
     *      Reader used to receive information
     *   Method Description:
     *   Sets up instance
     *   Method Return: None
     */
    public Connection(Socket socket, PrintWriter writer, BufferedReader reader){
        this.socket = socket;
        this.writer = writer;
        this.reader = reader;
    }

    /**
     *   Method Name: create
     *   Method Parameters:
     *   Socket socket:
     *      Socket used for connection
     *   Method Description:
     *   This method sets up a PrintWriter, Reader for a connection
     *   Method Return: Connection
     */
    public static Connection create(Socket socket) throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return new Connection(socket, out, in);
    }
    /**
     *   Method Name: send
     *   Method Parameters:
     *   String s:
     *      String to send
     *   Method Description:
     *   This method sends a string using a network connection
     *   Method Return: None
     */
    public void send(String s){
        this.writer.println(s);
    }

    /**
     *   Method Name: receive
     *   Method Parameters: None
     *   Method Description:
     *   This method reads the next line sent through the connection
     *   Method Return: String message
     */
    public String receive() throws IOException { return this.reader.readLine(); }


    /**
     *   Method Name: close
     *   Method Parameters: None
     *   Method Description:
     *   This method closes the socket
     *   Method Return: None
     */
    public void close() throws IOException{ this.socket.close(); }

}
