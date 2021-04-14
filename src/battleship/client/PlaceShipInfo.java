package battleship.client;

import battleship.Coordinates;
import javafx.stage.Stage;

public class PlaceShipInfo extends CustomInfo{
    private Coordinates prev;
    private Coordinates next;
    private Stage primaryStage;


    public PlaceShipInfo(Coordinates prev, Coordinates next, Stage primaryStage){
        this.prev = prev;
        this.next = next;
        this.primaryStage = primaryStage;
    }

    public Stage getStage(){ return this.primaryStage; }

    public Coordinates getPrev() {
        return this.prev;
    }

    public Coordinates getNext() {
        return this.next;
    }

}
