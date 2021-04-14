package battleship.client;

import battleship.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client2Test extends Application {

    Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BattleShip 2");

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
                playGame();
            }

        });

        mainGrid.add(playButton,0,0);
        mainGrid.add(helpButton,0,1);
        mainGrid.add(leaderBoardButton,0,2);
        mainScene = new Scene(mainGrid, 800,600);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    private static void playGame(){
        ClientGame localGame = new ClientGame("Player 2");
        Thread game = new Thread(localGame);
        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
