package battleship;

public class AircraftCarrier extends Ship{
    public static final int aircraftCarrierSize = 5;
    public AircraftCarrier(){
        this.size = aircraftCarrierSize;
        this.parts = new ShipPart[aircraftCarrierSize];
        this.name = "Aircraft Carrier";
    }
}
