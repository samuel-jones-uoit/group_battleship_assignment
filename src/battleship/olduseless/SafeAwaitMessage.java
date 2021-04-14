package battleship.olduseless;


import battleship.Connection;
import battleship.client.ClientGame;

public class SafeAwaitMessage implements Runnable{
    private Connection connection;
    private ClientGame game;
    public SafeAwaitMessage(Connection c, ClientGame g){
        this.connection = c;
        this.game = g;
    }
    @Override
    public void run() {
        try{
            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
