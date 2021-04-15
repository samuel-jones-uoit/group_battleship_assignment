package battleship.client;

import battleship.BoardTile;
import battleship.Coordinates;
import battleship.Player;
/**
 *   Class Name: ClientPlayer
 *   Class Description:
 *   Type of player which is used in the Client software
 */
public abstract class ClientPlayer extends Player {
    protected ClientGame battleShipGame; // store a reference to the current game
    protected ClientBoard board; // store a reference to its board

    /**
     *   Method Name: ClientPlayer
     *   Method Parameters:
     *   String name:
     *      Name of the player
     *   Method Description:
     *   Its a constructor
     *   Method Return: None
     */
    public ClientPlayer(String name) {
        super(name);
    }

    /**
     *   Method Name: setBattleShipGame
     *   Method Parameters:
     *   ClientGame g:
     *      Game reference
     *   Method Description:
     *   Setter method
     *   Method Return: None
     */
    public void setBattleShipGame(ClientGame g){
        this.battleShipGame = g;
    }

    /**
     *   Method Name: notify
     *   Method Parameters:
     *   String msg:
     *      Message
     *   String type:
     *      Type of message
     *   Method Description:
     *   This method notifies the user (when overridden in HumanPlayer). It exists to be overridden or be left empty
     *   Method Return: None
     */
    public void notify(String msg, String type){}

    /**
     *   Method Name: notifyError
     *   Method Parameters:
     *   String msg:
     *      Message
     *   String type:
     *      Type of message
     *   Method Description:
     *   This method notifies the user of an error (when overridden in HumanPlayer). It exists to be overridden or be left empty
     *   Method Return: None
     */
    public void notifyError(String msg, String type){}

    /**
     *   Method Name: getViewableBoard
     *   Method Parameters:
     *   ClientPlayer p:
     *      Viewer
     *   Method Description:
     *   This method gets a 2d array of BoardTiles that is viewable
     *   Method Return: BoardTile[][]
     */
    public BoardTile[][] getViewableBoard(ClientPlayer p){
        return this.board.getViewableBoard(p);
    }
    /**
     *   Method Name: hitSpot
     *   Method Parameters:
     *   Coordinates attackCoordinates:
     *      Attack certain coordinates
     *   Method Description:
     *   This method hits a spot on a board
     *   Method Return: None
     */
    public void hitSpot(Coordinates attackCoordinates) {
        this.board.hitSpot(attackCoordinates);
    }
}
