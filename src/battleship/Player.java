package battleship;
public abstract class Player {
    protected String name;
    protected Board board;
    protected Board enemyboard;
    protected BattleShipGame bsg;
    public Player(String name){ this.name = name; }
    public String getName(){
        return this.name;
    }
    public void setBoard(Board board){this. board = board;}
    public void setEnemyBoard(Board board){ this.enemyboard = board;}
    public void setBsg(BattleShipGame game){this.bsg = game;};
    public boolean is(Player p){
        return p.getName().equals(this.getName());
    }
    public abstract Coordinates getCoordinates();
    public abstract void notify(String msg);
    public abstract void setShips();

    public abstract void makeAttack();

    public abstract void showBoard();
}
