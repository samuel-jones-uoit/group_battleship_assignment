package battleship;

import java.util.Scanner;

public abstract  class Player {
    private String name;
    public Player(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public boolean is(Player p){
        return p.getName().equals(this.getName());
    }
    public abstract Coordinates getCoordinates();
    public abstract void notify(String msg);
}
