package gb.safronov.client.models;

import gb.safronov.client.controllers.ChatController;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    private static final String AUTH_CMD_PREFIX = "/auth"; // + login + password
    private static final String AUTHOK_CMD_PREFIX = "/authok"; // + username
    private static final String AUTHERR_CMD_PREFIX = "/autherr"; // + error message
    private static final String CLIENT_MSG_CMD_PREFIX = "/cm"; // + msg
    private static final String SERVER_MSG_CMD_PREFIX = "/sm"; // + msg
    private static final String PRIVATE_MSG_CMD_PREFIX = "/pm"; // + msg
    private static final String STOP_SERVER_CMD_PREFIX = "/stop";
    private static final String END_CLIENT_CMD_PREFIX = "/end";
    private static final String REFRESH_CLIENT_LIST = "/ref";

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8186;
    private DataInputStream in;
    private DataOutputStream out;

    private final String host;
    private final int port;

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network() {
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
    }


    public void connect() {
        try {
            Socket socket = new Socket(host, port);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Соединение не установлено");
        }
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при отправке сообщения");
        }
    }

    public void waitMessage(ChatController chatController) {
       Thread t = new Thread(() -> {
           try {
               while (true) {

                   String message = in.readUTF();

                   if (message.startsWith("/authok")) {
                       String[] splitMessage = message.split("\\s+", 2);

                       chatController.closeTheLoginWindow();

                       Platform.runLater(() -> chatController.setUsernameTitle(splitMessage[1]));


                   } else if (message.startsWith(SERVER_MSG_CMD_PREFIX)) {
                       String[] parts = message.split("\\s+", 2);
                       String serverMessage = parts[1];

                       Platform.runLater(() -> chatController.appendServerMessage(serverMessage));
                   } else if (message.startsWith(REFRESH_CLIENT_LIST)){

                       Platform.runLater(() -> chatController.setUserlist(message));


                   }
                   chatController.appendMessage("Я: " + message);
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       });

       t.setDaemon(true);
       t.start();

    }
}
