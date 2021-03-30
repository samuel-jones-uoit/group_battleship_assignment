package battleship;

public abstract class Ship {
    protected int size;
    protected ShipPart[] parts;
    protected String name;

    public int getSize(){
        return this.size;
    }
    public String getName(){ return this.name; }
    public ShipPart[] getParts(){
        return this.parts;
    }
    public boolean isAlive(){
        for (ShipPart part : this.parts){
            if (!(part.isHit())){
                return true;
            }
        }
        return false;
    }
}
