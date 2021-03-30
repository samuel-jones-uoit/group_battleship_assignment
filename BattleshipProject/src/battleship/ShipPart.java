package battleship;

public class ShipPart {
    private boolean hit;
    private Ship parent;
    public ShipPart(Ship parent){
        this.hit = false;
        this.parent = parent;
    }
    public Ship getParent(){
        return this.parent;
    }
    public boolean isHit(){
        return this.hit;
    }

    public void hit(){
        this.hit = true;
    }
}
