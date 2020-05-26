package quixx.client.models;

import java.util.regex.Pattern;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextFormatter;

public class JoinGameRequest {

    protected StringProperty nameProperty;
    protected BooleanBinding namePropertyIsNotEmpty;
    protected StringProperty joinGameCodeProperty;
    protected BooleanBinding joinGameCodePropertyIsNotEmpty;
    
    protected static final Pattern VALID_NAME_UPDATE = Pattern.compile("^[a-zA-Z0-9]{0,10}$");
    protected static final Pattern VALID_NAME_FINAL = Pattern.compile("^[a-zA-Z0-9]{1,10}$");
    protected static final Pattern VALID_CODE_UPDATE = Pattern.compile("^[a-zA-Z0-9]{0,10}$");
    protected static final Pattern VALID_CODE_FINAL = Pattern.compile("^[A-Z0-9]{0,10}$");

    public final TextFormatter<String> nameTextFormatter;
    public final TextFormatter<String> codeTextFormatter;
    

    public JoinGameRequest() {
        nameProperty = new SimpleStringProperty("");
        namePropertyIsNotEmpty = nameProperty.isNotEmpty();
        joinGameCodeProperty = new SimpleStringProperty("");
        joinGameCodePropertyIsNotEmpty = joinGameCodeProperty.isNotEmpty();


        nameTextFormatter = new TextFormatter<String>(change -> {
            if (!change.isContentChange())
                return change;
            if (VALID_NAME_FINAL.matcher(change.getControlNewText()).matches())
                return change;
            else if (VALID_NAME_UPDATE.matcher(change.getControlNewText()).matches()){
                // change.setText(change.getText().toUpperCase());
                return change;
            }
            return null;
        });

        codeTextFormatter = new TextFormatter<String>(change -> {
            if (!change.isContentChange())
                return change;
            if (VALID_CODE_FINAL.matcher(change.getControlNewText()).matches())
                return change;
            else if (VALID_CODE_UPDATE.matcher(change.getControlNewText()).matches()){
                change.setText(change.getText().toUpperCase());
                return change;
            }
            return null;
        });
    }
    
    public boolean isValidJoinGameCommand() {
        return VALID_NAME_FINAL.matcher(nameProperty.getValue()).matches() 
               && VALID_CODE_FINAL.matcher(joinGameCodeProperty.getValue()).matches();
    }

    /**
     * Generate command message for the server to request joining a game.
     */
    public String getJoinGameCommand() {
        assert (isValidJoinGameCommand());

        if (joinGameCodePropertyIsNotEmpty.get())
            return String.format("JOIN %s %s", nameProperty.get(), joinGameCodeProperty.get());
        else
            return String.format("JOIN %s", nameProperty.get());
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public StringProperty joinGameCodeProperty() {
        return joinGameCodeProperty;
    }

    public BooleanBinding namePropertyIsNotEmpty() {
        return namePropertyIsNotEmpty;
    }

    public BooleanBinding joinGameCodePropertyIsNotEmpty() {
        return joinGameCodePropertyIsNotEmpty;
    }
}