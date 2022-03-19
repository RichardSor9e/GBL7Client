module gb.safronov.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens gb.safronov.client to javafx.fxml;
    exports gb.safronov.client;
    exports gb.safronov.client.controllers;
    opens gb.safronov.client.controllers to javafx.fxml;
}