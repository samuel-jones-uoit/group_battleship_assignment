package battleship.client;

import battleship.Ship;
/**
 *   Class Name: UnknownShip
 *   Class Description:
 *   Used to Ships of an unknown type
 */
public class UnknownShip extends Ship {
    /**
     *   Method Name: Constructor
     *   Method Parameters: None
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public UnknownShip(){
        this.name = "UNKNOWN";
        this.size = -1;
    }
}
