package battleship.client;

import battleship.*;

/**
 *   Class Name: EnemyPlayer
 *   Class Description:
 *   Type of ClientPlayer -- not user
 */
public class EnemyPlayer extends ClientPlayer {
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   String name:
     *      Name
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public EnemyPlayer(String name){
        super(name);
    }
    /**
     *   Method Name: notify
     *   Method Parameters:
     *   String msg:
     *      Message
     *   Method Description:
     *   Blank (enemy doesn't need notifications)
     *   Method Return: None
     */
    public void notify(String msg){

    }

    /**
     *   Method Name: makeAttack
     *   Method Parameters:
     *   Coordinates attackCoordinates:
     *      Coordinates of attack
     *   Method Description:
     *   Attacks a spot
     *   Method Return: None
     */
    public void makeAttack(Coordinates attackCoordinates){
        this.battleShipGame.attack(this, attackCoordinates); // always successful
    }
    /**
     *   Method Name: setBoard
     *   Method Parameters:
     *   String s:
     *      Board represented by string
     *   Method Description:
     *   Setter
     *   Method Return: None
     */
    public void setBoard(String s){
        this.board = ClientBoard.makeBoard(s, this);
    }

}
