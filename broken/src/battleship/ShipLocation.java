package battleship;

public class ShipLocation {
    private int startX, startY, endX, endY;
    ShipLocation(int startX, int startY, int endX, int endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX(){
        return this.startX;
    }

    public int getStartY(){
        return this.startY;
    }

    public int getEndX(){
        return this.endX;
    }

    public int getEndY(){
        return this.endY;
    }
}
