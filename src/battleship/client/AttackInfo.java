package battleship.client;

import battleship.Coordinates;
/**
 *   Class Name: AttackInfo
 *   Class Description:
 *   Stores information about an attack
 */
public class AttackInfo extends CustomInfo{
    private final Coordinates coordinates;


    public AttackInfo (Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }
}
