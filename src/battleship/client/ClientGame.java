package battleship.client;

import battleship.*;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ClientGame {
    private final Connection con;
    private final HumanPlayer me;
    private final EnemyPlayer enemy;
    public ClientGame(Connection connection, PlayerSet players) {
        this.con = connection;
        this.me = (HumanPlayer) players.getPlayer1();
        this.enemy = (EnemyPlayer) players.getPlayer2();
        this.me.setConnection(connection);
        this.me.setBattleShipGame(this);
        this.enemy.setBattleShipGame(this);
    }

    public void start(){
        this.me.createBoard();
    }

    public void mainGame(){
        try{
            this.enemy.createBoard(con.receive());
            Thread t = new Thread(new SafeAwaitMessage(this.con, this));
            t.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean attack(ClientPlayer attacker, Coordinates attackCoordinates) {

        ClientPlayer victim;
        if (attacker.is(me)){
            victim = enemy;
        }else{
            victim = me;

        }
        if (!validateAttack(attackCoordinates, victim.getViewableBoard(victim))){
            System.out.println("Invalid attack!");
            attacker.notify("Invalid attack!");
            return false;
        }
        victim.hitSpot(attackCoordinates);
        if (attacker instanceof HumanPlayer){
            ((HumanPlayer) attacker).showBoard();
        }else{
            assert victim instanceof HumanPlayer;
            ((HumanPlayer) victim).showBoard();
        }
        return true;
    }

    private boolean validateAttack(Coordinates attackCoordinates, BoardTile[][] victimBoard) {
        BoardTile attackSpot = victimBoard[attackCoordinates.getRow()][attackCoordinates.getColumn()];
        return !(attackSpot.isHit());
    }

    public ClientPlayer getOtherPlayer(ClientPlayer p) {
        if (me.is(p)){
            return enemy;
        }else{
            return me;
        }
    }

    public void nextMove(){
        this.me.makeAttack();
        Thread t = new Thread(new SafeAwaitMessage(this.con, this));
        t.start();
    }

    public void getAttacked() {
        try{
            System.out.println("Waiting for coordinates");
            this.enemy.makeAttack(Coordinates.fromString(this.con.receive()));
            Thread t = new Thread(new SafeAwaitMessage(this.con, this));
            t.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void gameOver() {
        try{
            String winMessage = this.con.receive();
            new Alert (Alert.AlertType.INFORMATION, winMessage).showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void textNotification() {
        try{
            System.out.println(this.con.receive());
        }catch (Exception e){
            e.printStackTrace();
        }
        Thread t = new Thread(new SafeAwaitMessage(this.con, this));
        t.start();
        // TODO: Display in text thing
    }
}
