package battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {

        //launch(args);
        //BattleShipLobby lobby = new BattleShipLobby("localhost", 27015);
        //PlayerSet players = lobby.run();
        System.out.println("Welcome To Battleship!");
        Player p1 = new HumanPlayer("Samuel");
        Player p2 = new BotPlayer("StupidBot");
        PlayerSet playerSet = new PlayerSet(p1,p2);
        BattleShipGame game = new BattleShipGame(playerSet);
        game.start();
    }
}
