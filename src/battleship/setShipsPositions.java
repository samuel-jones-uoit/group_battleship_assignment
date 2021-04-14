package battleship;

import battleship.client.HumanPlayer;
import battleship.client.PlaceShipInfo;
import battleship.client.currMatchBoardPositions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class setShipsPositions {
    private static Scene setShipsScenes;
    public static Button[][] btns = new Button[10][10];
    private static Label[] numbersLabels = new Label[10];
    private static Label[] lettersLabels = new Label[10];
    private static Label instructions;
    private static HumanPlayer player1;
    private static Coordinates previous =null;

    public static void initDisplayBoard(Stage primaryStage) {
        //Set Ships Position Scene GridPane
        GridPane setShipsGrid = new GridPane();
        BorderPane setShipsBorder = new BorderPane();
        setShipsGrid.setAlignment(Pos.CENTER);
        setShipsGrid.setHgap(0);
        setShipsGrid.setVgap(0);

        initBtnsArray();
        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                setShipsGrid.add(btns[i][j],j+1,i+1);
            }
        }

        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            setShipsGrid.add(numbersLabels[i],i+1,12);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }

        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            setShipsGrid.add(lettersLabels[i],0, i+1);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        for (int i = 0; i <= 9;i++) {
            for(int j = 0; j <= 9;j++) {
                final int finalI = i;
                final int finalJ = j;
                btns[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        buttonPressed(finalI, finalJ, primaryStage);
                    }
                });
            }
        }

        //Changing Scene
        instructions = new Label();
        instructions.setAlignment(Pos.CENTER);
        HBox errors = new HBox(instructions);
        errors.setAlignment(Pos.CENTER);
        setShipsBorder.setTop(errors);
        setShipsBorder.setCenter(setShipsGrid);
        setShipsScenes = new Scene(setShipsBorder, 800,600);
        primaryStage.setScene(setShipsScenes);
    }

    private static void initBtnsArray() {

        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                btns[i][j] = new Button();
                btns[i][j].setMaxSize(32, 32);
                btns[i][j].setMinSize(32, 32);
                btns[i][j].setId("water");
                btns[i][j].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
            }
        }
    }

    private static void initNumberArray(){
        for(int i = 0; i<numbersLabels.length;i++){
            numbersLabels[i] = new Label(Integer.toString(i+1));
            numbersLabels[i].setPrefWidth(32);
            numbersLabels[i].setTextAlignment(TextAlignment.CENTER);
            numbersLabels[i].setWrapText(true);
        }
    }

    private static void initLetterArray(){
        for(int i = 0; i<lettersLabels.length;i++){
            String ch = Character.toString(((char) i+65));
            lettersLabels[i] = new Label(ch);
            lettersLabels[i].setPrefWidth(32);
            lettersLabels[i].setTextAlignment(TextAlignment.CENTER);
            lettersLabels[i].setWrapText(true);
        }
    }

    public static void setInstructions(String message){
        instructions.setText(message);
    }

    public static void setPlayer (HumanPlayer player){
        player1 = player;
    }

    private static void buttonPressed(int i, int j, Stage primaryStage){
        if(previous == null){
            previous = new Coordinates(i,j);
            return;
        }
        Coordinates current = new Coordinates(i,j);
        // TODO: maybe get rid of these 2 temp variables
        Coordinates tempPrevious = previous;
        Stage tempPrimaryStage = primaryStage;
        player1.addToDo(new PlaceShipInfo(tempPrevious, current, tempPrimaryStage));
        previous = null;
    }

    public static void changeScene(Stage primaryStage) {
        currMatchBoardPositions.initDisplayBoard(primaryStage);
    }
    public static void displayBoard(String[][] symbols){
        for (int i = 0; i < symbols.length; i++){
            for (int j = 0; j < symbols.length; j++){
                btns[i][j].setId(symbols[i][j]);
            }
        }
    }
}
