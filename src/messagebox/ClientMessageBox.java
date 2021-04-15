package messagebox;
/**
 *   Class Name: ClientMessageBox
 *   Class Description:
 *   Stores messages
 */
public class ClientMessageBox extends MessageBox {
    private MessageUI parentUI;

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   int maxMessages:
     *      The maximum amount of messages to store
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public ClientMessageBox(int maxMessages){
        super(maxMessages);
    }

    /**
     *   Method Name: setParentUI
     *   Method Parameters: None
     *   Method Description:
     *   Setter
     *   Method Return: None
     */
    public void setParentUI(MessageUI parentUI){
        this.parentUI = parentUI;
    }

    /**
     *   Method Name: addMessage
     *   Method Parameters:
     *   String msg:
     *      Message
     *   Method Description:
     *   Add a message to the box
     *   Method Return: None
     */
    @Override
    public void addMessage(String msg){
        String newMessage = "GAME" + ": " + msg;
        this.moveUp();
        this.messages[this.maxMessages - 1] = newMessage;
        incMessageCount();
        parentUI.update();
    }
}