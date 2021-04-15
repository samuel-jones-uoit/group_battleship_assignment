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

/**
 *   Class Name: setShipsPositions
 *   Class Description:
 *   UI class for the ship placing scene used during the game
 */
public class setShipsPositions {
    public static Button[][] buttons = new Button[10][10];
    private static final Label[] numbersLabels = new Label[10];
    private static final Label[] lettersLabels = new Label[10];
    private static Label instructions;
    private static Label errorField;
    private static HumanPlayer player1;
    private static Coordinates previous = null;

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
        GridPane setShipsGrid = new GridPane();
        BorderPane setShipsBorder = new BorderPane();
        setShipsGrid.setAlignment(Pos.CENTER);
        setShipsGrid.setHgap(0);
        setShipsGrid.setVgap(0);

        // player 1 board
        initBtnsArray();
        for(int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                setShipsGrid.add(buttons[i][j],j+1,i+1);
            }
        }

        // initialize the numbers you see on the side
        initNumberArray();
        for(int i = 0; i<numbersLabels.length; i++){
            setShipsGrid.add(numbersLabels[i],i+1,12);
            numbersLabels[i].setAlignment(Pos.CENTER);
        }

        // initializes the letters you see on the bottom
        initLetterArray();
        for(int i = 0; i<lettersLabels.length; i++){
            setShipsGrid.add(lettersLabels[i],0, i+1);
            lettersLabels[i].setAlignment(Pos.CENTER);
        }

        for (int i = 0; i <= 9;i++) {
            for(int j = 0; j <= 9;j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        buttonPressed(finalI, finalJ, primaryStage);
                    }
                });
            }
        }

        //Changing Scene
        instructions = new Label("Waiting For Players");
        instructions.setAlignment(Pos.CENTER);
        HBox errors = new HBox(instructions);

        errorField = new Label();
        HBox placementErrors = new HBox(errorField);
        errors.setAlignment(Pos.CENTER);
        GridPane errorGrid = new GridPane();
        errorGrid.add(errors,0,0);
        errorGrid.add(errorField,0,1);
        errorGrid.setAlignment(Pos.CENTER);
        setShipsBorder.setTop(errorGrid);



        setShipsBorder.setCenter(setShipsGrid);
        Scene setShipsScenes = new Scene(setShipsBorder, 800, 600);
        primaryStage.setScene(setShipsScenes);
    }

    /**
     *   Method Name: initBtnsArray
     *   Method Parameters: None
     *   Method Description:
     *   Initializes the player 1 buttons
     *   Method Return: None
     */
    private static void initBtnsArray() {

        for(int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMaxSize(32, 32);
                buttons[i][j].setMinSize(32, 32);
                buttons[i][j].setId("water");
                buttons[i][j].getStylesheets().addAll(setShipsPositions.class.getResource("style.css").toExternalForm());
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
     *   Method Name: setInstructions
     *   Method Parameters:
     *   String message:
     *      message for player
     *   Method Description:
     *   Gives the players instructions
     *   Method Return: None
     */
    public static void setInstructions(String message){
        instructions.setText(message);
    }

    /**
     *   Method Name: setError
     *   Method Parameters:
     *   String message:
     *      message for player
     *   Method Description:
     *   Gives the players error message
     *   Method Return: None
     */
    public static void setError(String message){
        errorField.setText(message);
    }

    /**
     *   Method Name: setPlayer
     *   Method Parameters:
     *   HumanPlayer player:
     *      message for player
     *   Method Description:
     *   Setter
     *   Method Return: None
     */
    public static void setPlayer (HumanPlayer player){
        player1 = player;
    }

    /**
     *   Method Name: buttonPressed
     *   Method Parameters:
     *   int i:
     *      row index
     *   int j:
     *      column index
     *   Method Description:
     *   Handles a button press
     *   Method Return: None
     */
    private static void buttonPressed(int i, int j, Stage primaryStage){
        // Protects from premature clicks
        if (player1 == null) {
            return;
        }

        if(previous == null){
            previous = new Coordinates(i,j);
            return;
        }
        Coordinates current = new Coordinates(i,j);
        // TODO: maybe get rid of these 2 temp variables
        //Coordinates tempPrevious = previous;
        //Stage tempPrimaryStage = primaryStage;
        player1.addToDo(new PlaceShipInfo(previous, current, primaryStage), "b4");
        previous = null;
    }

    /**
     *   Method Name: changeScene
     *   Method Parameters:
     *   Stage primaryStage:
     *      Stage for the program
     *   Method Description:
     *   Changes scene
     *   Method Return: None
     */
    public static void changeScene(Stage primaryStage) {
        currMatchBoardPositions.initDisplayBoard(primaryStage);
    }

    /**
     *   Method Name: displayBoard
     *   Method Parameters:
     *   String[][] symbols:
     *      2d array of symbol strings
     *   Method Description:
     *   Updates a 2d array of buttons
     *   Method Return: None
     */
    public static void displayBoard(String[][] symbols){
        for (int i = 0; i < symbols.length; i++){
            for (int j = 0; j < symbols.length; j++){
                buttons[i][j].setId(symbols[i][j]);
            }
        }
    }
}
