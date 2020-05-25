package quixx.client.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CreateGameRequest extends JoinGameRequest {

    public static final int MAXPLAYERS_MIN = 2;
    public static final int MAXPLAYERS_MAX = 8;
    public static final int MAXPLAYERS_DEFAULT = 5;
    
    protected DoubleProperty maxPlayersProperty;
    protected BooleanProperty publicProperty;


    public CreateGameRequest() {
        super();
        maxPlayersProperty = new SimpleDoubleProperty(MAXPLAYERS_DEFAULT);
        publicProperty = new SimpleBooleanProperty(false);
    }

    public boolean isValidCreateGameCommand() {
        return VALID_NAME_FINAL.matcher(nameProperty.getValue()).matches() 
               && VALID_CODE_FINAL.matcher(joinGameCodeProperty.getValue()).matches();
    }

    /**
     * Generate command message for the server to request joining a game.
     */
    public String getCreateGameCommand() {
        assert (isValidCreateGameCommand());

        String publicToken = publicProperty.get() ? "PUBLIC" : "PRIVATE"; 

        if (joinGameCodePropertyIsNotEmpty.get())
            return String.format("CREATE %s %.0f %s %s", nameProperty.get(), maxPlayersProperty.get(), publicToken, joinGameCodeProperty.get());
        else
            return String.format("CREATE %s %.0f %s", nameProperty.get(), maxPlayersProperty.get(), publicToken);
    }


    public DoubleProperty maxPlayersProperty(){
        return maxPlayersProperty;
    }

    public BooleanProperty publicProperty() {
        return publicProperty;
    }


}