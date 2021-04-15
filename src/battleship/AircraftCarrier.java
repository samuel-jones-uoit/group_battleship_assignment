package battleship;

/**
 *   Class Name: AircraftCarrier
 *   Class Description:
 *   A type of ship used in the game
 */
public class AircraftCarrier extends Ship{
    public static final int aircraftCarrierSize = 5;
    /**
     *   Method Name: Constructor
     *   Method Parameters: None
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public AircraftCarrier(){
        this.size = aircraftCarrierSize;
        this.parts = new ShipPart[aircraftCarrierSize];
        this.name = "Aircraft Carrier";
    }
}
