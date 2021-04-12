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
            player1.createBoard();
            player2.createBoard();
            player1.notify("ENEMY_BOARD");
            player2.notify("ENEMY_BOARD");
            player1.notify(player2.getBoard().toString(player1));
            player2.notify(player1.getBoard().toString(player2));
            while (player1.isAlive() && player2.isAlive()){
                player1.makeAttack();
                System.out.println(player1.getName());
                player1.print();
                System.out.println(player2.getName());
                player2.print();
                if (!player2.isAlive()){
                    break;
                }
                player2.makeAttack();
                System.out.println(player1.getName());
                player1.print();
                System.out.println(player2.getName());
                player2.print();
            }
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

    public RemotePlayer getOtherPlayer(RemotePlayer asker){
        if (asker.is(player1)){
            return this.player2;
        }else{
            return this.player1;
        }
    }

}
