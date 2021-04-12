package messagebox;

public abstract class ChatBox {
    protected int maxMessages;
    protected String[] messages;
    protected int messageCount;
    public ChatBox(int maxMessages){
        this.maxMessages = maxMessages;
        this.messages = new String[maxMessages];
        this.messageCount = 0;
    }
    public int getSize() {
        return this.maxMessages;
    }

    protected void moveUp(){
        for (int i = Math.max(0, this.maxMessages - this.messageCount -1); i < this.maxMessages - 1; i++){
            this.messages[i] = this.messages[i + 1];
        }
    }
    public abstract void addMessage(String username, String msg);

    protected void incMessageCount(){
        if (this.messageCount < this.maxMessages){
            this.messageCount++;
        }
    }

    public String getMessage(int index){
        return this.messages[index];
    }

    public int getMessageCount(){
        return this.messageCount;
    }
}
