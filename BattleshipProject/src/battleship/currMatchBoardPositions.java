package battleship;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class currMatchBoardPositions {
    private static Scene currMatchBoard;
    public static Button[][] btns = new Button[10][10];
    private static Label[] numbersLabels = new Label[10];
    private static Label[] lettersLabels = new Label[10];

    public static void initDisplayBoard(Stage primaryStage) {
        //Set Ships Position Scene GridPane
        GridPane player1currMatchBoardGrid = new GridPane();
        player1currMatchBoardGrid.setAlignment(Pos.CENTER_LEFT);
        player1currMatchBoardGrid.setHgap(0);
        player1currMatchBoardGrid.setVgap(0);


        //player1
        initBtnsArray();
        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                player1currMatchBoardGrid.add(btns[i][j],i+1,j);
            }
        }
        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            player1currMatchBoardGrid.add(numbersLabels[i],i+1,11);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }
        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            player1currMatchBoardGrid.add(lettersLabels[i],0, i);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        GridPane player2currMatchBoardGrid = new GridPane();
        player2currMatchBoardGrid.setAlignment(Pos.CENTER_RIGHT);
        player2currMatchBoardGrid.setHgap(0);
        player2currMatchBoardGrid.setVgap(0);

        //player2
        initBtnsArray();
        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                player2currMatchBoardGrid.add(btns[i][j],i+15,j);
            }
        }
        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            player2currMatchBoardGrid.add(numbersLabels[i],i+15,11);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }
        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            player2currMatchBoardGrid.add(lettersLabels[i],14, i);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        //Changing Scene
        currMatchBoard = new Scene(player2currMatchBoardGrid, 800,600);
        currMatchBoard.add(player1currMatchBoardGrid);
        primaryStage.setScene(currMatchBoard);
        System.out.println("Clicked on Play button");
    }

    private static void initBtnsArray() {

        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                btns[i][j] = new Button();
                btns[i][j].setMaxSize(32, 32);
                btns[i][j].setMinSize(32, 32);
                btns[i][j].setId("waterBlocks");
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
}
