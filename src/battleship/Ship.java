package battleship;

public abstract class Ship {
    protected int size;
    protected int health;
    protected ShipPart[] parts;
    public Ship(int size){
        this.size = size;
        this.health = size;
        this.parts = new ShipPart[size];
    }

    public boolean isSetup(){
        if (parts[0] != null){
            return true;
        }
        return false;
    }


}
