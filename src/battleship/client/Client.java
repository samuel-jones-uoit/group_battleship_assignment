package battleship.client;

import battleship.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
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
                playGame();
            }

        });


        mainGrid.add(playButton,0,1);
        mainScene = new Scene(mainGrid, 800,600);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        //playButton For Play Button
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setShipsPositions.initDisplayBoard(primaryStage);
                playGame();
            }
        });

    }
    private static void playGame(){
        ClientGame localGame = new ClientGame("Player 1");
        Thread game = new Thread(localGame);
        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
