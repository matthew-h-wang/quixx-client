module quixx.client {
    requires javafx.controls;
    requires javafx.fxml;
    exports quixx.client;
    opens quixx.client to javafx.fxml;
    opens quixx.client.views to javafx.fxml;
}