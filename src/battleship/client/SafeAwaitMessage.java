package battleship.client;


import battleship.Connection;

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
            String message = connection.receive();
            System.out.println("RECEIVED: " + message);
            if (message.equals("ENEMY_BOARD")){
                game.mainGame();
            }else if (message.equals("YOUR_MOVE")){
                game.nextMove();
            }else if (message.equals("ATTACK_ON_YOU")){
                game.getAttacked();
            }else if (message.equals("GAME_OVER")){
                game.gameOver();
            }else if (message.equals("TEXT NOTIFICATION")){
                game.textNotification();
            }
            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
