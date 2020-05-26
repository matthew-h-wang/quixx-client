package quixx.client;

import java.util.Map;
import java.util.Map.Entry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import quixx.client.models.Board;

public class BoardController {

    @FXML
    GridPane colorRowsGridPane;
    
    @FXML
    GridPane redRowGridPane;
    @FXML
    GridPane yellowRowGridPane;
    @FXML
    GridPane greenRowGridPane;
    @FXML
    GridPane blueRowGridPane;
    @FXML
    GridPane penaltiesGridPane;
    
    private Map<String, GridPane> colorToRow;

    @FXML
    Label redScoreLabel;
    @FXML
    Label yellowScoreLabel;
    @FXML
    Label greenScoreLabel;
    @FXML
    Label blueScoreLabel;
    @FXML
    Label penaltyScoreLabel;
    @FXML
    Label totalScoreLabel;

    private App mainApp;

    private final Board model;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public BoardController(){
        model = new Board();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        colorToRow = Map.of(
            Board.RED, redRowGridPane,
            Board.YELLOW, yellowRowGridPane,
            Board.GREEN, greenRowGridPane,
            Board.BLUE, blueRowGridPane
        );

        //Set all buttons of the board to be like checkboxes.
        //Bind model boolean properties to the pressing of buttons
        for (Entry<String, GridPane> e : colorToRow.entrySet()){
            for (Node n: e.getValue().getChildren()) {
                Button b = (Button) n;
                //handle lock button //TODO
                if (b.getText().isEmpty()) {
                    b.setDisable(true);
                    continue;
                }
                //
                System.out.println(b.getText());
                int i = GridPane.getColumnIndex(b); 
                b.setOnAction(a -> {
                    //Mark as checked
                    model.getBox(e.getKey(), Integer.parseInt(b.getText())).set(true);
                    b.setGraphic(new ButtonCheck());
                    //Disable all buttons to the left.
                    for (Node x: e.getValue().getChildren())
                        if (i >= GridPane.getColumnIndex(x)) 
                            x.setDisable(true);
                });
            }
        }

        for(Node n: penaltiesGridPane.getChildren()){
            if (! (n instanceof Button))
                continue;
            Button b = (Button) n;
            int i = GridPane.getColumnIndex(b); 
            b.setOnAction(a -> {
                //Mark as checked
                model.getPenaltyBox(i).set(true);
                b.setGraphic(new Label("X"));
            });

        }

        //Bind scores to the model, which updates automatically based on checkBoxes;
        redScoreLabel.textProperty().bind(model.getScore(Board.RED).asString());
        yellowScoreLabel.textProperty().bind(model.getScore(Board.YELLOW).asString());
        greenScoreLabel.textProperty().bind(model.getScore(Board.GREEN).asString());
        blueScoreLabel.textProperty().bind(model.getScore(Board.BLUE).asString());
        penaltyScoreLabel.textProperty().bind(model.getScore(Board.PENALTY).asString());
        totalScoreLabel.textProperty().bind(model.getTotalScore().asString());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    // /**
    //  * 
    //  */
    // @FXML protected void handleCreateButtonAction(ActionEvent e) {

    // }

    // /**
    //  * 
    //  */
    // @FXML protected void handleJoinButtonAction(ActionEvent e) {

    // }
    
    class ButtonCheck extends Label{
        public ButtonCheck(){
            super("X");
            this.setFont(new Font(32));
        }
    }
}