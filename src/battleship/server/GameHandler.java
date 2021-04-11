package battleship.server;

import battleship.*;

import java.io.IOException;

public class GameHandler implements Runnable{
    private RemotePlayer player1;
    private RemotePlayer player2;
    public GameHandler(Connection p1Connection, Connection p2Connection) {
        try{
            player1 = new RemotePlayer(p1Connection.receive(), p1Connection, this);
            player2 = new RemotePlayer(p2Connection.receive(), p2Connection, this);
            player2.notify(player1.getName());
            player1.notify(player2.getName());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        try{
            String p2Instructions = player1.createBoard();
            String p1Instructions = player2.createBoard();
            player1.notify("ENEMY_BOARD");
            player2.notify("ENEMY_BOARD");
            player1.notify(p1Instructions);
            player2.notify(p2Instructions);
            while (player1.isAlive() && player2.isAlive()){
                System.out.println("making p1 attack");
                player1.makeAttack();
                System.out.println("is p2 alive?");
                if (!player2.isAlive()){
                    System.out.println("no");
                    break;
                }
                System.out.println("yes");
                System.out.println("Making p2 attack");
                player2.makeAttack();
            }
            System.out.println("Exit loop!");
        }catch (IOException e){
            e.printStackTrace();
        }

        if (player1.isAlive()){
            System.out.println(player1.getName() + " won!");
            player1.notify("GAME_OVER");
            player2.notify("GAME_OVER");
            player1.notify(player1.getName() + " won!");
            player2.notify(player1.getName() + " won!");
        }else{
            System.out.println(player2.getName() + " won!");
            player1.notify("GAME_OVER");
            player2.notify("GAME_OVER");
            player1.notify(player2.getName() + " won!");
            player2.notify(player2.getName() + " won!");
        }
    }

    public void attack(RemotePlayer attacker, Coordinates attackCoordinates) {
        RemotePlayer victim;
        if (attacker.is(player1)){
            victim = player2;
        }else{
            victim = player1;
        }
        victim.hitSpot(attackCoordinates, attacker);;
    }

}
