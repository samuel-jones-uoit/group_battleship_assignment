package battleship.client;

import battleship.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.concurrent.RunnableFuture;

public class ClientGame implements Runnable{
    private Connection con;
    private HumanPlayer me;
    private EnemyPlayer enemy;
    private final String name;

    public ClientGame(String name){
        this.name = name;
    }
    public void run(){
        try{
            ClientJoiner joiner = new ClientJoiner("localhost", 16789);
            JoinInfo joinInfo = joiner.join(this.name);
            Connection connection = joinInfo.getConnection();
            PlayerSet players = joinInfo.getPlayers();
            this.con = connection;
            this.me = (HumanPlayer) players.getPlayer1();
            this.enemy = (EnemyPlayer) players.getPlayer2();
            this.me.setConnection(connection);
            this.me.setBattleShipGame(this);
            this.enemy.setBattleShipGame(this);
            System.out.println("Welcome To Battleship!");
            this.start();
        }catch (IOException | RejectedJoinException e){
            e.printStackTrace();
        }
    }


    public void start(){
        this.me.createBoard();
    }

    public void mainGame(){
        try{
            this.enemy.setBoard(con.receive());
            this.me.showBoard();
            waitNextMessage();
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
            attacker.notify("Invalid attack!");
            return false;
        }
        victim.hitSpot(attackCoordinates);
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
    }

    public void getAttacked() {
        try{
            this.enemy.makeAttack(Coordinates.fromString(this.con.receive()));
            this.me.showBoard();
            waitNextMessage();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void gameOver() {
        try{
            String winMessage = this.con.receive();
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(winMessage);
                    alert.showAndWait();
                    System.exit(0);
                }
            });
            System.out.println(winMessage);
            con.close();
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
        waitNextMessage();
    }

    public void waitNextMessage(){
        try{
            String message = con.receive();
            System.out.println("RECEIVED: " + message);
            if (message.equals("ENEMY_BOARD")){
                mainGame();
            }else if (message.equals("YOUR_MOVE")){
                nextMove();
            }else if (message.equals("ATTACK_ON_YOU")){
                getAttacked();
            }else if (message.equals("GAME_OVER")){
                gameOver();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
