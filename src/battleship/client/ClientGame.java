package battleship.client;

import battleship.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
/**
 *   Class Name: ClientGame
 *   Class Description:
 *   BattleShip Game instance
 */
public class ClientGame implements Runnable{
    private Connection connection;
    private HumanPlayer me; // player running the game
    private EnemyPlayer enemy; // other player
    private final String name; // player name

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   String name:
     *      Name of user
     *   Method Description:
     *   This method creates a game
     *   Method Return: None
     */
    public ClientGame(String name){
        this.name = name;
    }
    /**
     *   Method Name: run
     *   Method Parameters: None
     *   Method Description:
     *   This method connects to server then starts the game
     *   Method Return: None
     */
    public void run(){
        try{
            // start the lobby joining thing
            ClientJoiner joiner = new ClientJoiner("localhost", 16789);
            JoinInfo joinInfo = joiner.join(this.name);
            Connection connection = joinInfo.getConnection();
            PlayerSet players = joinInfo.getPlayers();
            this.connection = connection;
            this.me = (HumanPlayer) players.getPlayer1();
            this.enemy = (EnemyPlayer) players.getPlayer2();
            this.me.setConnection(connection);
            this.me.setBattleShipGame(this);
            this.enemy.setBattleShipGame(this);
            // start the game
            this.start();
        }catch (IOException | RejectedJoinException e){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gameOver("Unable to join game. Closing application.");
                }
            });
        }
    }

    /**
     *   Method Name: start
     *   Method Parameters: None
     *   Method Description:
     *   This method starts a game
     *   Method Return: None
     */
    public void start(){
        this.me.createBoard();
    }

    /**
     *   Method Name: mainGame
     *   Method Parameters: None
     *   Method Description:
     *   This method sets the enemy board and player board at the start of the game
     *   Method Return: None
     */
    public void startOfGame(){
        try{
            this.enemy.setBoard(connection.receive());
            this.me.showBoard();
            waitNextMessage();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *   Method Name: attack
     *   Method Parameters:
     *   ClientPlayer attacker:
     *      Player who is attacking
     *   Method Description:
     *   This method facilitates an attack on one player by another
     *   Method Return: boolean (success)
     */
    public boolean attack(ClientPlayer attacker, Coordinates attackCoordinates) {
        ClientPlayer victim;
        // if human player is attacking then victim is enemy and other way around
        victim = getOtherPlayer(attacker);

        // if the attack fails
        if (!validateAttack(attackCoordinates, victim.getViewableBoard(victim))){
            attacker.notify("Invalid attack!");
            return false;
        }
        // hit the spot on the board
        victim.hitSpot(attackCoordinates);
        return true;
    }

    /**
     *   Method Name: validateAttack
     *   Method Parameters:
     *   Coordinates attackCoordinates:
     *      Stage used by the game
     *   BoardTile[][] victimBoard
     *   Method Description:
     *   This method validates an attack
     *   Method Return: boolean (valid)
     */
    private boolean validateAttack(Coordinates attackCoordinates, BoardTile[][] victimBoard) {
        BoardTile attackSpot = victimBoard[attackCoordinates.getRow()][attackCoordinates.getColumn()];
        return !(attackSpot.isHit()); // if the spot isn't hit already its a valid attack
    }

    /**
     *   Method Name: getOtherPlayer
     *   Method Parameters:
     *   ClientPlayer p:
     *      Player asking
     *   Method Description:
     *   This method returns the other player (given a player)
     *   Example:
     *      Input: Human --> get enemy
     *      Input: enemy --> get Human
     *   Method Return: None
     */
    public ClientPlayer getOtherPlayer(ClientPlayer player) {
        if (me.is(player)){
            return enemy;
        }else{
            return me;
        }
    }

    /**
     *   Method Name: nextMove
     *   Method Parameters: None
     *   Method Description:
     *   This tells the player to make an attack
     *   Method Return: None
     */
    public void nextMove(){
        this.me.makeAttack();
    }

    /**
     *   Method Name: getAttacked
     *   Method Parameters: None
     *   Method Description:
     *   This method facilitates the player getting attacked
     *   Method Return: None
     */
    public void getAttacked() {
        try{
            this.enemy.makeAttack(Coordinates.fromString(this.connection.receive()));
            this.me.showBoard();
            waitNextMessage();
        }catch (IOException e){
            gameOver("Network error.");
        }
    }

    /**
     *   Method Name: gameOver
     *   Method Parameters: None
     *   Method Description:
     *   This method handles the event of one player winning the game
     *   Method Return: None
     */
    public void gameOver() {
        try{
            String winMessage = this.connection.receive();
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Winner Information");
                    alert.setHeaderText(null);
                    alert.setContentText(winMessage);
                    alert.showAndWait();
                    System.exit(0);
                }
            });
            connection.close();
        }catch (IOException e){
            gameOver("Network error.");
        }
    }

    /**
     *   Method Name: gameOver
     *   Method Parameters:
     *   String msg:
     *      Message to be displayed to the user
     *   Method Description:
     *   This method handles the event of a major error in the game
     *   Method Return: None
     */
    public void gameOver(String msg) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Crash");
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
                System.exit(0);
            }
        });
    }

    /**
     *   Method Name: waitNextMessage
     *   Method Parameters:
     *   Method Description:
     *   This method awaits and handles the next message it receives from the server
     *   Method Return: None
     */
    public void waitNextMessage(){
        try{
            String message = connection.receive();
            if (message.equals("ENEMY_BOARD")){
                startOfGame();
            }else if (message.equals("YOUR_MOVE")){
                nextMove();
            }else if (message.equals("ATTACK_ON_YOU")){
                getAttacked();
            }else if (message.equals("GAME_OVER")){
                gameOver();
            }
        }catch (IOException e){
            gameOver("Network error.");
        }
    }
}
