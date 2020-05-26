package quixx.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * JavaFX App
 */
public class App extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Node createJoinScreen;
    private Node board;
    


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
        rootLayout.setCenter(board);
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
        // loader = new FXMLLoader();
        // loader.setLocation(App.class.getResource("views/CreateJoinScreen.fxml"));
        // createJoinScreen = (Node) loader.load();
        // CreateJoinScreenController createJoinScreenController = loader.getController();
        // createJoinScreenController.setMainApp(this);

        //CreateBoard
        loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("views/Board.fxml"));
        board = (Node) loader.load();
        BoardController boardController = loader.getController();
        boardController.setMainApp(this);
    }



    /**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}