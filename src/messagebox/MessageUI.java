package messagebox;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *   Class Name: MessageUI
 *   Class Description:
 *   Displays messages
 */
public class MessageUI {
    private final int MESSAGEBOX_WIDTH;
    private final int MESSAGEBOX_HEIGHT;
    private final ClientMessageBox chatBox;
    private final int SIZE;
    private Label[] display;

    /**
     *   Method Name: Constructor
     *   Method Parameters:
     *   final int MESSAGEBOX_WIDTH:
     *      Width for messages
     *   final int MESSAGEBOX_HEIGHT:
     *      Height for messages
     *   ClientMessageBox chatBox:
     *      Chatbox
     *   Method Description:
     *   Regular constructor
     *   Method Return: None
     */
    public MessageUI(final int MESSAGEBOX_WIDTH, final int MESSAGEBOX_HEIGHT, ClientMessageBox chatBox){
        this.MESSAGEBOX_WIDTH = MESSAGEBOX_WIDTH;
        this.MESSAGEBOX_HEIGHT = MESSAGEBOX_HEIGHT;
        this.chatBox = chatBox;
        this.SIZE = chatBox.getSize();
        this.chatBox.setParentUI(this);
    }

    /**
     *   Method Name: setup
     *   Method Parameters: None
     *   Method Description:
     *   Sets up the UI
     *   Method Return: GridPane
     */
    public GridPane setup(){
        final double BOTTOM_PADDING = 20;
        final double LABEL_WIDTH = (double) this.MESSAGEBOX_WIDTH * 9 / 10;
        final double LABEL_HEIGHT = (this.MESSAGEBOX_HEIGHT - BOTTOM_PADDING) / (this.SIZE + 1);

        GridPane pane = new GridPane();

        display = new Label[this.SIZE];
        for (int i = 0 ; i < this.SIZE; i++){
            display[i] = new Label();
            display[i].setMinSize(LABEL_WIDTH, LABEL_HEIGHT);
            display[i].setMaxSize(LABEL_WIDTH, LABEL_HEIGHT);
            pane.add(display[i], 0, i);
        }
        return pane;
    }
    /**
     *   Method Name: update
     *   Method Parameters: None
     *   Method Description:
     *   Updates messages
     *   Method Return: None
     */
    public void update(){
        String msg;
        for (int i = this.SIZE - this.chatBox.getMessageCount(); i < this.chatBox.getSize(); i++){
            msg = this.chatBox.getMessage(i);
            if (msg == null){ continue; }
            this.display[i].setText(msg);
        }
    }
}