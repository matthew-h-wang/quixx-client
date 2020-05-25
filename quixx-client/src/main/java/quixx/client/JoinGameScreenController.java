package quixx.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import quixx.client.models.JoinGameRequest;

public class JoinGameScreenController {
    @FXML
    private TextField playerNameTextField;
    @FXML
    private TextField joinGameCodeTextField;
    @FXML
    private Button joinGameButton;

    private App mainApp;

    private final JoinGameRequest model;


    public JoinGameScreenController(){
        model = new JoinGameRequest();
    }

    @FXML
    private void initialize() {
        playerNameTextField.textProperty().bindBidirectional(model.nameProperty());
        playerNameTextField.setTextFormatter(model.nameTextFormatter);

        joinGameCodeTextField.textProperty().bindBidirectional(model.joinGameCodeProperty());
        joinGameCodeTextField.setTextFormatter(model.codeTextFormatter);

        joinGameButton.disableProperty().bind(model.namePropertyIsNotEmpty().not());
    }

    @FXML protected void handleJoinGameButtonAction(ActionEvent e) {
        if (model.isValidJoinGameCommand())
            System.out.println(model.getJoinGameCommand());
        else
            System.out.println("NOT VALID JOIN");
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public JoinGameRequest getModel(){
        return model;
    }
}