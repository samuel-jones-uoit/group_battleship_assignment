package messagebox;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.awt.event.KeyEvent;

public class ChatUI {
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;
    private ClientChatBox chatBox;
    private final int SIZE;
    private Label[] display;
    private String userName;
    public ChatUI(final int WINDOW_WIDTH, final int WINDOW_HEIGHT, ClientChatBox chatBox, String userName){
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.chatBox = chatBox;
        this.SIZE = chatBox.getSize();
        this.chatBox.setParentUI(this);
        this.userName = userName;
    }

    public GridPane setup(){
        final double BOTTOM_PADDING = 20;
        final double LABEL_WIDTH = (double) this.WINDOW_WIDTH  * 9 / 10;
        final double LABEL_HEIGHT = (this.WINDOW_HEIGHT - BOTTOM_PADDING) / (this.SIZE + 1);
        final double BUTTON_WIDTH = 50;
        final double BUTTON_HEIGHT = 50;
        final double TEXTFIELD_WIDTH = this.WINDOW_WIDTH - BUTTON_WIDTH;
        final double TEXTFIELD_HEIGHT = LABEL_HEIGHT;
        GridPane pane = new GridPane();

        TextField textField = new TextField();
        textField.setMaxSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().getCode() == KeyEvent.VK_ENTER){
                String text = textField.getText();
                if (!text.equals("")){
                    this.chatBox.addMessage(this.userName, text);
                    textField.setText("");
                }
            }
        });
        pane.add(textField, 0, this.SIZE);

        Button sendButton = new Button("Send");
        sendButton.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        sendButton.setOnAction(actionEvent -> {
            String text = textField.getText();
            if (!text.equals("")){
                this.chatBox.addMessage(this.userName, text);
                textField.setText("");
            }
        });
        pane.add(sendButton, 1, this.SIZE);
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
