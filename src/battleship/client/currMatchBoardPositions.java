package battleship.client;

import battleship.client.HumanPlayer;
import battleship.setShipsPositions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class currMatchBoardPositions {
    private static Scene currMatchBoard;
    public static Button[][] btnsP1 = new Button[10][10];
    public static Button[][] btnsP2 = new Button[10][10];
    private static Label[] numbersLabels = new Label[10];
    private static Label[] lettersLabels = new Label[10];
    private static HumanPlayer player1;

    public static void setPlayer1(HumanPlayer p1){
        player1 = p1;
    }
    public static void initDisplayBoard(Stage primaryStage) {
        //Set Ships Position Scene GridPane
        GridPane currMatchBoardGrid = new GridPane();
        currMatchBoardGrid.setAlignment(Pos.CENTER);
        currMatchBoardGrid.setHgap(0);
        currMatchBoardGrid.setVgap(0);


        //player1
        initBtnsP1Array();
        for(int i = 0; i < btnsP1.length; i++) {
            for (int j = 0; j < btnsP1.length; j++) {
                currMatchBoardGrid.add(btnsP1[i][j],j+1,i);
            }
        }
        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            currMatchBoardGrid.add(numbersLabels[i],i+1,11);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }
        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            currMatchBoardGrid.add(lettersLabels[i],0, i);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        //attack
        initBtnsArray();
        for(int i = 0; i < btnsP2.length; i++) {
            for (int j = 0; j < btnsP2.length; j++) {
                currMatchBoardGrid.add(btnsP2[i][j],i+15,j);
                final int finalI = i;
                final int finalJ = j;
                btnsP2[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        buttonPressed(finalI, finalJ, primaryStage);
                    }
                });
            }
        }
        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            currMatchBoardGrid.add(numbersLabels[i],i+15,11);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }
        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            currMatchBoardGrid.add(lettersLabels[i],14, i);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        //Changing Scene
        currMatchBoard = new Scene(currMatchBoardGrid, 800,600);
        primaryStage.setScene(currMatchBoard);
        System.out.println("Clicked on Play button");
    }

    private static void buttonPressed(int finalI, int finalJ, Stage primaryStage) {
        player1.GUIAttack(finalI, finalJ);
    }

    private static void initBtnsArray() {

        for(int i = 0; i < btnsP2.length; i++) {
            for (int j = 0; j < btnsP2.length; j++) {
                btnsP2[i][j] = new Button();
                btnsP2[i][j].setMaxSize(32, 32);
                btnsP2[i][j].setMinSize(32, 32);
                btnsP2[i][j].setId("water");
                btnsP2[i][j].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
            }
        }
    }

    private static void initBtnsP1Array() {

        for(int i = 0; i < btnsP1.length; i++) {
            for (int j = 0; j < btnsP1.length; j++) {
                btnsP1[i][j] = new Button();
                btnsP1[i][j].setMaxSize(32, 32);
                btnsP1[i][j].setMinSize(32, 32);
                btnsP1[i][j].setId("water");
                btnsP1[i][j].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
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


    public static void displayLeftBoard(String[][] myBoardGUI) {
        displayBoard(myBoardGUI, btnsP1);
    }

    public static void displayRightBoard(String[][] enemyBoardGUI) {
        displayBoard(enemyBoardGUI, btnsP2);
    }

    private static void displayBoard(String[][] symbols, Button[][] buttons){
        for (int i = 0; i < symbols.length; i++){
            for (int j = 0; j < symbols.length; j++){
                buttons[i][j].setId(symbols[i][j]);
            }
        }
    }
}
