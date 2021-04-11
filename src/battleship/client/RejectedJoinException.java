package battleship.client;

public class RejectedJoinException extends Exception{
    public RejectedJoinException(String msg){
        super(msg);
    }
}
