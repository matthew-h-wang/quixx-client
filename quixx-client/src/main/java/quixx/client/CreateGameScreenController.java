package quixx.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import quixx.client.models.CreateGameRequest;
import quixx.client.models.JoinGameRequest;

public class CreateGameScreenController {
    @FXML
    private TextField playerNameTextField;
    @FXML
    private TextField joinGameCodeTextField;
    @FXML
    private Slider maxPlayersSlider;
    @FXML
    private CheckBox makePublicCheckBox;
    @FXML
    private Button createGameButton;

    private App mainApp;

    private final CreateGameRequest model;


    public CreateGameScreenController(){
        model = new CreateGameRequest();
    }

    @FXML
    private void initialize() {
        playerNameTextField.textProperty().bindBidirectional(model.nameProperty());
        playerNameTextField.setTextFormatter(model.nameTextFormatter);

        joinGameCodeTextField.textProperty().bindBidirectional(model.joinGameCodeProperty());
        joinGameCodeTextField.setTextFormatter(model.codeTextFormatter);

        maxPlayersSlider.valueProperty().bindBidirectional(model.maxPlayersProperty());

        makePublicCheckBox.selectedProperty().bindBidirectional(model.publicProperty());    
        
        createGameButton.disableProperty().bind(model.namePropertyIsNotEmpty().not());

    }

    @FXML protected void handleCreateGameButtonAction(ActionEvent e) {
        //TODO
        if (model.isValidCreateGameCommand())
            System.out.println(model.getCreateGameCommand());
        else
            System.out.println("NOT VALID CREATE");
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public CreateGameRequest getModel(){
        return model;
    }
}