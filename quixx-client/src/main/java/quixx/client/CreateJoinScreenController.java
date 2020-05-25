package quixx.client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
// import quixx.client.App;

public class CreateJoinScreenController {
        @FXML 
        private HBox centerAreaHBox;
        @FXML   
        private VBox buttonsVBox;
        @FXML
        private Button joinButton;
        @FXML
        private Button createButton;

        
        private Node createGameScreen;
        private Node joinGameScreen;
    
        private App mainApp;

        /**
         * The constructor.
         * The constructor is called before the initialize() method.
         */
        public CreateJoinScreenController(){

        }

        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() throws IOException {

            //Create CreateGameScreen
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/CreateGameScreen.fxml"));
            createGameScreen = (Node) loader.load();
            CreateGameScreenController createGameScreenController = loader.getController();
            HBox.setHgrow(createGameScreen, Priority.ALWAYS);

            //Create JoinGameScreen
            loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/JoinGameScreen.fxml"));
            joinGameScreen = (Node) loader.load();
            JoinGameScreenController joinGameScreenController = loader.getController();
            HBox.setHgrow(joinGameScreen, Priority.ALWAYS);

            //Both screens might as well share the name and code data;
            createGameScreenController.getModel().nameProperty().bindBidirectional(joinGameScreenController.getModel().nameProperty());
            createGameScreenController.getModel().joinGameCodeProperty().bindBidirectional(joinGameScreenController.getModel().joinGameCodeProperty());

        }

        /**
         * Is called by the main application to give a reference back to itself.
         * 
         * @param mainApp
         */
        public void setMainApp(App mainApp) {
            this.mainApp = mainApp;
        }

        /**
         * 
         */
        @FXML protected void handleCreateButtonAction(ActionEvent e) {
            centerAreaHBox.getChildren().clear();
            centerAreaHBox.getChildren().addAll(buttonsVBox, createGameScreen);
        }

        /**
         * 
         */
        @FXML protected void handleJoinButtonAction(ActionEvent e) {
            centerAreaHBox.getChildren().clear();
            centerAreaHBox.getChildren().addAll(buttonsVBox, joinGameScreen);
        }
        


}