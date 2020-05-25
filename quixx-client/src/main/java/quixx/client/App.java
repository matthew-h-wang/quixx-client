package quixx.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import quixx.client.views.CreateJoinScreenController;

/**
 * JavaFX App
 */
public class App extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Node createJoinScreen;
    
    

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Quixx App");
        try {
            setupPartials();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        rootLayout.setCenter(createJoinScreen);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void setupPartials() throws IOException {
        //Root Layout
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("views/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        //CreateJoinScreen
        loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("views/CreateJoinScreen.fxml"));
        createJoinScreen = (Node) loader.load();
        CreateJoinScreenController createJoinScreenController = loader.getController();
        createJoinScreenController.setMainApp(this);
    }



    /**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}