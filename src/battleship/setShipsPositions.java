package battleship;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class setShipsPositions {
    private static Scene setShipsScenes;
    public static Button[][] btns = new Button[10][10];
    private static Label[] numbersLabels = new Label[10];
    private static Label[] lettersLabels = new Label[10];

    public static void initDisplayBoard(Stage primaryStage) {
        //Set Ships Position Scene GridPane
        GridPane setShipsGrid = new GridPane();
        setShipsGrid.setAlignment(Pos.CENTER);
        setShipsGrid.setHgap(0);
        setShipsGrid.setVgap(0);

        initBtnsArray();
        for(int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                setShipsGrid.add(btns[i][j],i+1,j);
            }
        }

        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            setShipsGrid.add(numbersLabels[i],i+1,11);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }

        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            setShipsGrid.add(lettersLabels[i],0, i);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        //Changing Scene
        setShipsScenes = new Scene(setShipsGrid, 800,600);
        primaryStage.setScene(setShipsScenes);
        System.out.println("Clicked on Play button");

        currMatchBoardPositions.initDisplayBoard(primaryStage);
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