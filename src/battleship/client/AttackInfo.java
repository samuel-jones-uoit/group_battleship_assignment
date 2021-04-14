package battleship.client;

import battleship.Coordinates;

public class AttackInfo extends CustomInfo{
    private final Coordinates coordinates;


    public AttackInfo (Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }
}
