package battleship.client;

import battleship.Coordinates;
import battleship.setShipsPositions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import messagebox.ClientMessageBox;
import messagebox.MessageUI;
/**
 *   Class Name: currMatchBoardPositions
 *   Class Description:
 *   UI class for the main scene used during the game
 */
public class currMatchBoardPositions {
    public static Button[][] btnsP1 = new Button[10][10]; // Player 1's board representation
    public static Button[][] btnsP2 = new Button[10][10]; // Player 2's board representation
    private static final Label[] numbersLabels = new Label[10];
    private static final Label[] lettersLabels = new Label[10];
    private static HumanPlayer player1;
    private static ClientMessageBox msgBox;

    /**
     *   Method Name: setPlayer1
     *   Method Parameters:
     *   HumanPlayer p1:
     *      Player1
     *   Method Description:
     *   Setter method
     *   Method Return: None
     */
    public static void setPlayer1(HumanPlayer p1){
        player1 = p1;
    }

    /**
     *   Method Name: initDisplayBoard
     *   Method Parameters:
     *   Stage primaryStage:
     *      Stage to use
     *   Method Description:
     *   Initializes the scene
     *   Method Return: None
     */
    public static void initDisplayBoard(Stage primaryStage) {
        //Set Ships Position Scene GridPane
        BorderPane borderPane = new BorderPane();

        msgBox = new ClientMessageBox(10); // max 10 messages displayed
        MessageUI msgUI = new MessageUI(450, 250, msgBox);
        msgBox.setParentUI(msgUI);
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

        // initialize the numbers you see on the side
        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            currMatchBoardGrid.add(numbersLabels[i],i+1,11);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }
        // initializes the letters you see on the bottom
        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            currMatchBoardGrid.add(lettersLabels[i],0, i);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        // enemy board
        initBtnsArray();
        for(int i = 0; i < btnsP2.length; i++) {
            for (int j = 0; j < btnsP2.length; j++) {
                currMatchBoardGrid.add(btnsP2[i][j],j+15,i);
                final int finalI = i;
                final int finalJ = j;
                btnsP2[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        buttonPressed(finalI, finalJ);
                    }
                });
            }
        }
        // initializes the numbers you see on the side of the enemy board
        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            currMatchBoardGrid.add(numbersLabels[i],i+15,11);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }
        // initializes the letters you see on the bottom of the enemy board
        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            currMatchBoardGrid.add(lettersLabels[i],14, i);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        //Changing Scene
        borderPane.setCenter(currMatchBoardGrid);
        borderPane.setTop(msgUI.setup());
        Scene currGameScene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(currGameScene);
    }

    /**
     *   Method Name: buttonPressed
     *   Method Parameters:
     *   int finalI:
     *      row index
     *   int finalJ:
     *      column index
     *   Method Description:
     *   Adds a desired attack spot by the player to a queue
     *   Method Return: None
     */
    private static void buttonPressed(int finalI, int finalJ) {
        player1.addToDo(new AttackInfo(new Coordinates(finalI, finalJ)));
    }

    /**
     *   Method Name: initBtnsArray
     *   Method Parameters: None
     *   Method Description:
     *   Initializes the player 2 buttons
     *   Method Return: None
     */
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

    /**
     *   Method Name: initBtnsP1Array
     *   Method Parameters: None
     *   Method Description:
     *   Initializes the player 1 buttons
     *   Method Return: None
     */
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

    /**
     *   Method Name: initNumberArray
     *   Method Parameters: None
     *   Method Description:
     *   Initializes the number array for the side of the screen
     *   Method Return: None
     */
    private static void initNumberArray(){
        for(int i = 0; i<numbersLabels.length;i++){
            numbersLabels[i] = new Label(Integer.toString(i+1));
            numbersLabels[i].setPrefWidth(32);
            numbersLabels[i].setTextAlignment(TextAlignment.CENTER);
            numbersLabels[i].setWrapText(true);
        }
    }
    /**
     *   Method Name: initLetterArray
     *   Method Parameters: None
     *   Method Description:
     *   Initializes the letter at the bottom of the screen
     *   Method Return: None
     */
    private static void initLetterArray(){
        for(int i = 0; i<lettersLabels.length;i++){
            String ch = Character.toString(((char) i+65));
            lettersLabels[i] = new Label(ch);
            lettersLabels[i].setPrefWidth(32);
            lettersLabels[i].setTextAlignment(TextAlignment.CENTER);
            lettersLabels[i].setWrapText(true);
        }
    }

    /**
     *   Method Name: displayLeftBoard
     *   Method Parameters:
     *   String[][] myBoardGUI:
     *      2d array of symbol strings
     *   Method Description:
     *   Updates the left board
     *   Method Return: None
     */
    public static void displayLeftBoard(String[][] myBoardGUI) {
        displayBoard(myBoardGUI, btnsP1);
    }

    /**
     *   Method Name: displayRightBoard
     *   Method Parameters:
     *   String[][] enemyBoardGUI:
     *      2d array of symbol strings
     *   Method Description:
     *   Updates the right board
     *   Method Return: None
     */
    public static void displayRightBoard(String[][] enemyBoardGUI) {
        displayBoard(enemyBoardGUI, btnsP2);
    }

    /**
     *   Method Name: displayBoard
     *   Method Parameters:
     *   String[][] myBoardGUI:
     *      2d array of symbol strings
     *   Button[][] buttons:
     *      2d array of buttons
     *   Method Description:
     *   Updates a 2d array of buttons
     *   Method Return: None
     */
    private static void displayBoard(String[][] symbols, Button[][] buttons){
        for (int i = 0; i < symbols.length; i++){
            for (int j = 0; j < symbols.length; j++){
                buttons[i][j].setId(symbols[i][j]);
            }
        }
    }

    /**
     *   Method Name: addMessage
     *   Method Parameters:
     *   String msg:
     *      Message to add to message box
     *   Method Description:
     *   Updates the message box
     *   Method Return: None
     */
    public static void addMessage(String msg){
        msgBox.addMessage(msg);
    }
}
