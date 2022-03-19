package gb.safronov.client;

import gb.safronov.client.controllers.ChatController;
import gb.safronov.client.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class StartClient extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();

        Network network = new Network();
        ChatController chatController = fxmlLoader.getController();

        chatController.setNetwork(network);

        network.connect();
        network.waitMessage(chatController);

    }

    public static void main(String[] args) {
        launch();
    }
}