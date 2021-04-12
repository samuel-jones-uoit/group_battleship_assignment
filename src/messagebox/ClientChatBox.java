package messagebox;

public class ClientChatBox extends ChatBox{
    private ChatUI parentUI;

    public ClientChatBox(int maxMessages){
        super(maxMessages);
    }

    public void setParentUI(ChatUI parentUI){
        this.parentUI = parentUI;
    }
    @Override
    public void addMessage(String username, String msg){
        String newMessage = username + ": " + msg;
        this.moveUp();
        this.messages[this.maxMessages - 1] = newMessage;
        incMessageCount();
        parentUI.update();
    }

    public void addRemoteMessage(String username, String msg){
        String newMessage = username + ": " + msg;
        this.moveUp();
        this.messages[this.maxMessages - 1] = newMessage;
        incMessageCount();
        parentUI.update();
    }
}
