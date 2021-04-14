package messagebox;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MessageUI {
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;
    private final ClientMessageBox chatBox;
    private final int SIZE;
    private Label[] display;

    public MessageUI(final int WINDOW_WIDTH, final int WINDOW_HEIGHT, ClientMessageBox chatBox){
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.chatBox = chatBox;
        this.SIZE = chatBox.getSize();
        this.chatBox.setParentUI(this);
    }

    public GridPane setup(){
        final double BOTTOM_PADDING = 20;
        final double LABEL_WIDTH = (double) this.WINDOW_WIDTH  * 9 / 10;
        final double LABEL_HEIGHT = (this.WINDOW_HEIGHT - BOTTOM_PADDING) / (this.SIZE + 1);
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

    public void update(){
        String msg;
        for (int i = this.SIZE - this.chatBox.getMessageCount(); i < this.chatBox.getSize(); i++){
            msg = this.chatBox.getMessage(i);
            if (msg == null){ continue; }
            this.display[i].setText(msg);
        }
    }
}