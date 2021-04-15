package battleship.server;

import battleship.*;

import java.io.IOException;
/**
 *   Class Name: GameHandler
 *   Class Description:
 *   Handles the game from the server POV
 */
public class GameHandler implements Runnable{
    private RemotePlayer player1;
    private RemotePlayer player2;

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   Connection p1Connection:
     *      Connection to Player 1
     *   Connection p2Connection:
     *      Connection to Player 2
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public GameHandler(Connection p1Connection, Connection p2Connection) {
        try{
            player1 = new RemotePlayer(p1Connection.receive(), p1Connection, this);
            player2 = new RemotePlayer(p2Connection.receive(), p2Connection, this);
            player2.notify(player1.getName());
            player1.notify(player2.getName());
        }catch (IOException e){
            System.err.println("Error running game.");
            shutDown();
        }
    }

    /**
     *   Method Name: run
     *   Method Parameters: None
     *   Method Description:
     *   Runs the game as a server
     *   Method Return: None
     */
    public void run() {
        try{
            player1.createBoard();
            player2.createBoard();
            player1.notify("ENEMY_BOARD");
            player2.notify("ENEMY_BOARD");
            // send players eachothers boards
            player1.notify(player2.getBoard().toString(player1));
            player2.notify(player1.getBoard().toString(player2));
            // while both alive
            while (player1.isAlive() && player2.isAlive()){
                player1.makeAttack();
                if (!player2.isAlive()){
                    break;
                }
                player2.makeAttack();
            }
        }catch (IOException e){
            System.err.println("Error running game.");
            shutDown();
        }

        player1.notify("GAME_OVER");
        player2.notify("GAME_OVER");
        if (player1.isAlive()){
            System.out.println(player1.getName() + " won!");
            player1.notify(player1.getName() + " won!");
            player2.notify(player1.getName() + " won!");
        }else if (player2.isAlive()){
            System.out.println(player2.getName() + " won!");
            player1.notify(player2.getName() + " won!");
            player2.notify(player2.getName() + " won!");
        }else{
            player1.notify("Error D:");
            player2.notify("Error D:");
        }
        shutDown();
    }

    /**
     *   Method Name: attack
     *   Method Parameters:
     *   RemotePlayer attacker:
     *      Player currently attacking
     *   Coordinates attackCoordinates:
     *      Coordinates of attack spot
     *   Method Description:
     *   Runs the game as a server
     *   Method Return: None
     */
    public void attack(RemotePlayer attacker, Coordinates attackCoordinates) {
        RemotePlayer victim;
        victim = getOtherPlayer(attacker);
        victim.hitSpot(attackCoordinates, attacker);;
    }

    /**
     *   Method Name: getOtherPlayer
     *   Method Parameters:
     *   ClientPlayer p:
     *      Player asking
     *   Method Description:
     *   This method returns the other player (given a player)
     *   Example:
     *      Input: Human --> get Enemy
     *      Input: Enemy --> get Human
     *   Method Return: None
     */
    public RemotePlayer getOtherPlayer(RemotePlayer asker){
        if (asker.is(player1)){
            return this.player2;
        }else{
            return this.player1;
        }
    }

    /**
     *   Method Name: shutDown
     *   Method Parameters: None
     *   Method Description:
     *   Turns off the game
     *   Method Return: None
     */
    public void shutDown(){
        player1.closeConnection();
        player2.closeConnection();
        Thread.currentThread().interrupt();
    }

}
