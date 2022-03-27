package gb.safronov.client.controllers;

import gb.safronov.client.models.Network;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ChatController {

    @FXML
    private ListView<String> usersList;

    @FXML
    private Label usernameTitle;

    @FXML
    private TextArea chatHistory;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private AnchorPane startLoginWindow1;

    @FXML
    private GridPane startLoginWindow2;

    private String selectedRecipient;

    @FXML
    public void initialize() {
        usersList.setItems(FXCollections.observableArrayList("Тимофей", "Дмитрий", "Диана", "Арман"));
        loginButton.setOnAction(actionEvent -> sendLoginMessage());
        sendButton.setOnAction(event -> sendMessage());
        inputField.setOnAction(event -> sendMessage());

        usersList.setCellFactory(lv -> {
            MultipleSelectionModel<String> selectionModel = usersList.getSelectionModel();
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                usersList.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                        selectedRecipient = null;
                    } else {
                        selectionModel.select(index);
                        selectedRecipient = cell.getItem();
                    }
                    event.consume();
                }
            });
            return cell;
        });



    }

    private void sendLoginMessage() {
        String loginMessage = loginTextField.getText().trim();
        String passwordMessage = passwordTextField.getText().trim();

        loginTextField.clear();
        passwordTextField.clear();

        if (loginMessage.trim().isEmpty() || passwordMessage.trim().isEmpty()) {
            return;
        }

        network.sendMessage("/auth " + loginMessage + " " + passwordMessage);


    }

    private Network network;

    public void setNetwork(Network network) {
        this.network = network;
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        inputField.clear();


        if (message.trim().isEmpty()) {
            return;
        }

        network.sendMessage(message);

        appendMessage(message);
    }

    public void appendMessage(String message) {
        chatHistory.appendText(message);
        chatHistory.appendText(System.lineSeparator());
    }


    public void closeTheLoginWindow() {
        startLoginWindow1.setVisible(false);
        startLoginWindow2.setVisible(false);
        chatHistory.appendText("You are online!");
        chatHistory.appendText(System.lineSeparator());


    }
     public void setUsernameTitle (String name) {
         this.usernameTitle.setText(name);

    }
    public void appendServerMessage(String serverMessage) {
        chatHistory.appendText(serverMessage);
        chatHistory.appendText(System.lineSeparator());

    }
public void setUserlist(String a) {
String[] s;

      s = a.split("\\s+", 2);
      s[1] = s[1].replaceAll("[,.]", "");
    s[1] = s[1].replaceAll("]", "");
    s[1] = s[1].replaceAll("\\[", "");


    System.out.println(s[1]);


    usersList.setItems(null);
    String[] splited = s[1].split("\\s+");
    usersList.setItems(FXCollections.observableArrayList(splited));



}


}
//hththth
