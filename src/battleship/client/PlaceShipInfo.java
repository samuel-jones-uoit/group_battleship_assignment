package battleship.client;

import battleship.Coordinates;
import javafx.stage.Stage;
/**
 *   Class Name: PlaceShipInfo
 *   Class Description:
 *   Info about the placement of a ship
 */
public class PlaceShipInfo extends CustomInfo{
    private final Coordinates end1;
    private final Coordinates end2;
    private final Stage primaryStage;

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   Coordinates prev:
     *      One end of ship
     *   Coordinates next:
     *      Other end of ship
     *   Stage primaryStage:
     *      Stage used by program
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public PlaceShipInfo(Coordinates prev, Coordinates next, Stage primaryStage){
        this.end1 = prev;
        this.end2 = next;
        this.primaryStage = primaryStage;
    }

    /**
     *   Method Name: getStage
     *   Method Parameters:
     *   Method Description:
     *   Getter
     *   Method Return: Stage
     */
    public Stage getStage(){ return this.primaryStage; }

    /**
     *   Method Name: getEnd1
     *   Method Parameters:
     *   Method Description:
     *   Getter
     *   Method Return: Coordinates
     */
    public Coordinates getEnd1() {
        return this.end1;
    }

    /**
     *   Method Name: getEnd2
     *   Method Parameters:
     *   Method Description:
     *   Getter
     *   Method Return: Coordinates
     */
    public Coordinates getEnd2() {
        return this.end2;
    }

}
