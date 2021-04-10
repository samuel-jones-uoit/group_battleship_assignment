package battleship;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class currMatchBoardPositions {
    private static Scene currMatchBoard, currMatchBoard2;
    public static Button[][] btns = new Button[10][10];
    private static Label[] numbersLabels = new Label[10];
    private static Label[] lettersLabels = new Label[10];

    public static void initDisplayBoard(Stage primaryStage) {
        //Set Ships Position Scene GridPane
        GridPane currMatchBoardGrid = new GridPane();
        currMatchBoardGrid.setAlignment(Pos.CENTER);
        currMatchBoardGrid.setHgap(0);
        currMatchBoardGrid.setVgap(0);


        //player1
        initBtnsArray();
        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                currMatchBoardGrid.add(btns[i][j],i+1,j);

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

        //player2
        initBtnsArray();
        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                currMatchBoardGrid.add(btns[i][j],i+15,j);
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
