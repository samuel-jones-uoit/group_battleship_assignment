package messagebox;
public class ClientMessageBox extends MessageBox {
    private MessageUI parentUI;

    public ClientMessageBox(int maxMessages){
        super(maxMessages);
    }

    public void setParentUI(MessageUI parentUI){
        this.parentUI = parentUI;
    }
    @Override
    public void addMessage(String msg){
        String newMessage = "GAME" + ": " + msg;
        this.moveUp();
        this.messages[this.maxMessages - 1] = newMessage;
        incMessageCount();
        parentUI.update();
    }
}