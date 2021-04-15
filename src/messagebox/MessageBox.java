package messagebox;
/**
 *   Class Name: MessageBox
 *   Class Description:
 *   Abstract message box class
 */
public abstract class MessageBox {
    protected int maxMessages;
    protected String[] messages;
    protected int messageCount;
    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   int maxMessages:
     *      The maximum amount of messages to store
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public MessageBox(int maxMessages){
        this.maxMessages = maxMessages;
        this.messages = new String[maxMessages];
        this.messageCount = 0;
    }

    /**
     *   Method Name: getSize
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: int
     */
    public int getSize() {
        return this.maxMessages;
    }

    /**
     *   Method Name: moveUp
     *   Method Parameters: None
     *   Method Description:
     *   Moves messages up
     *   Method Return: None
     */
    protected void moveUp(){
        for (int i = Math.max(0, this.maxMessages - this.messageCount -1); i < this.maxMessages - 1; i++){
            this.messages[i] = this.messages[i + 1];
        }
    }

    public abstract void addMessage(String msg);

    /**
     *   Method Name: incMessageCount
     *   Method Parameters: None
     *   Method Description:
     *   Increase message count
     *   Method Return: None
     */
    protected void incMessageCount(){
        if (this.messageCount < this.maxMessages){
            this.messageCount++;
        }
    }

    /**
     *   Method Name: getMessage
     *   Method Parameters:
     *   int index:
     *      index of message
     *   Method Description:
     *   Getter
     *   Method Return: String
     */
    public String getMessage(int index){
        return this.messages[index];
    }

    /**
     *   Method Name: getMessageCount
     *   Method Parameters: None
     *   Method Description:
     *   Getter
     *   Method Return: int
     */
    public int getMessageCount(){
        return this.messageCount;
    }
}