package battleship;

/**
 *   Class Name: Player
 *   Class Description:
 *   Abstract player class
 */
public abstract class Player {
    protected String name;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   String name:
     *      name of player
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public Player(String name){ this.name = name; }

    /**
     *   Method Name: getName
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: String
     */
    public String getName(){
        return this.name;
    }

    /**
     *   Method Name: is
     *   Method Parameters:
     *   Player p:
     *      another player
     *   Method Description:
     *   Checks if two players are the same
     *   Method Return: boolean -- True if same, false if not
     */
    public boolean is(Player p){
        return p.getName().equals(this.getName());
    }
    public abstract void notify(String msg);

}
