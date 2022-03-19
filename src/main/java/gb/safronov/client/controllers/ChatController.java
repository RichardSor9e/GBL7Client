package gb.safronov.client.controllers;

import gb.safronov.client.models.Network;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    @FXML
    public void initialize() {
        usersList.setItems(FXCollections.observableArrayList("Тимофей", "Дмитрий", "Диана", "Арман"));
        loginButton.setOnAction(actionEvent -> sendLoginMessage());
        sendButton.setOnAction(event -> sendMessage());
        inputField.setOnAction(event -> sendMessage());
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



}
//hththth
