package battleship;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    Scene mainScene, playScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("BattleShip");

        //Main Menu Scene GridPane
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        //Main Menu Buttons
        Button playButton = new Button("Play");
        playButton.setPrefWidth(200);
        Button helpButton = new Button("Help");
        helpButton.setPrefWidth(200);
        Button leaderBoardButton = new Button("Leader Board");
        leaderBoardButton.setPrefWidth(200);

        //playButton For Play Button
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setShipsPositions.initDisplayBoard(primaryStage);
            }
        });

        mainGrid.add(playButton,0,0);
        mainGrid.add(helpButton,0,1);
        mainGrid.add(leaderBoardButton,0,2);
        mainScene = new Scene(mainGrid, 800,600);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
        //BattleShipLobby lobby = new BattleShipLobby("localhost", 27015);
        //PlayerSet players = lobby.run();
        //System.out.println("Welcome To Battleship!");
       // Player p1 = new HumanPlayer("Samuel");
       // Player p2 = new BotPlayer("Stupid Bot");
     //   PlayerSet playerSet = new PlayerSet(p1, p2);
     //   BattleShipGame game = new BattleShipGame(playerSet);
      //  game.start();
    }
}
