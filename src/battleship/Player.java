package battleship;

public abstract class Player {
    protected String name;
    public Player(String name){ this.name = name; }
    public String getName(){
        return this.name;
    }
    public boolean is(Player p){
        return p.getName().equals(this.getName());
    }
    public abstract void notify(String msg);
}
