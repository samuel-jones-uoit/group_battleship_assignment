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
/**
 *   Class Name: Client
 *   Class Description:
 *   BattleShip Player
 */
public class Client extends Application {

    /**
     *   Method Name: start
     *   Method Parameters:
     *   Stage primaryStage:
     *      Stage used by the game
     *   Method Description:
     *   This method sets up the UI
     *   Method Return: None
     */
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

        //playButton For Play Button
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setShipsPositions.initDisplayBoard(primaryStage);
                playGame();
            }

        });


        mainGrid.add(playButton,0,1);
        Scene mainScene = new Scene(mainGrid, 800, 600);
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

    /**
     *   Method Name: playGame
     *   Method Parameters: None
     *   Method Description:
     *   This method starts a game of BattleShip
     *   Method Return: None
     */
    private static void playGame(){
        ClientGame localGame = new ClientGame("Player 1");
        Thread game = new Thread(localGame);
        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
