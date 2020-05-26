package quixx.client.models;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.beans.Observable;
import javafx.util.Callback;

public class Board {
    public static final String RED = "red";
    public static final String YELLOW = "yellow";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String PENALTY = "penalty";
    public static final int PENALTY_POINTS = 0;

    public static final String[] COLORS = {RED, YELLOW, GREEN, BLUE};
    public static final String[] SCORES = {RED, YELLOW, GREEN, BLUE, PENALTY};
    public static final int[] NUMBERS = {2,3,4,5,6,7,8,9,10,11,12};

    public static final int MAX_PENALTIES = 4;

    private HashMap<String, ObservableMap<Integer, BooleanProperty>> boxes;

    private ObservableMap<String, IntegerProperty> scores;

    private ObservableList<BooleanProperty> penalties;

    private IntegerProperty totalScore;

    public Board(){
        setupProperties();
        bindProperties();
    }

    private void setupProperties(){
        boxes = new HashMap<>();
        scores = FXCollections.observableHashMap();
        for (String c : COLORS){
            ObservableMap<Integer, BooleanProperty> row = FXCollections.observableHashMap();
            for (int n : NUMBERS)
                row.put(n, new SimpleBooleanProperty(false));
            boxes.put(c, row);
        }

        for (String c : SCORES){
            scores.put(c, new SimpleIntegerProperty());
        }

        // From https://stackoverflow.com/questions/36257939/bindings-count-number-of-true-booleanproperty-in-an-observable-list
        // penalties = FXCollections.observableArrayList((Callback<BooleanProperty, Observable[]>) e -> new Observable[] {e});
        penalties = FXCollections.observableArrayList();
        for (int i = 0; i < MAX_PENALTIES; i++)
            penalties.add(new SimpleBooleanProperty());

        totalScore = new SimpleIntegerProperty();
    }

    private void bindProperties(){
        // Stream code borrowed from https://stackoverflow.com/questions/36257939/bindings-count-number-of-true-booleanproperty-in-an-observable-list
        
        //Bind the score of each colored row to a math function that depends on the count of marked boxes
        for (String c : COLORS) {
            // IntegerBinding countRow = Bindings.createIntegerBinding(() -> (int) getRow(c).entrySet().stream().map(entry -> entry.getValue().get()).filter(b -> b).count(), getRow(c));
            // scores.get(c).bind(countRow.add(1).multiply(countRow).divide(2));

            for (Entry<Integer, BooleanProperty> e : getRow(c).entrySet()){
                BooleanProperty bp = e.getValue();
                bp.addListener(b -> {
                    int count = 0;
                    for (Entry<Integer, BooleanProperty> e2 : getRow(c).entrySet()){
                        if(e2.getValue().get())
                            count++;
                    }
                    scores.get(c).set(count * (count + 1) / 2);
                });
            }
        }

        //Bind the score of penalties to the count of marked penalty boxes
        // IntegerBinding countPenalties = Bindings.createIntegerBinding(() -> (int) penalties.stream().map(BooleanProperty::get).filter(b -> b).count(), penalties);
        // countPenalties.addListener(e -> System.out.println(e));
        // scores.get(PENALTY).bind(countPenalties.multiply(PENALTY_POINTS));
        for (BooleanProperty bp : penalties) {
            bp.addListener(b -> {
                int count = 0;
                for (BooleanProperty bp2: penalties){
                    if(bp2.get())
                        count++;
                }
                scores.get(PENALTY).set(count * PENALTY_POINTS);
            });
        }

        //Bind the total score to the scores of the rows and penalties
        for (Entry<String,IntegerProperty> e : scores.entrySet()) {
            IntegerProperty ip = e.getValue();
            ip.addListener(i -> {
                int sum = 0;
                for (Entry<String,IntegerProperty> e2 : scores.entrySet()) {
                    sum += e2.getValue().get();
                }
                totalScore.set(sum);
            });
        }
        // totalScore.bind(Bindings.createIntegerBinding(() -> (int) scores.entrySet().stream().mapToInt(entry -> entry.getValue().get()).sum(), scores));
    }

    public ObservableMap<Integer, BooleanProperty> getRow(String color){
        return boxes.get(color);
    }

    public BooleanProperty getBox(String color, int number){
        return boxes.get(color).get(number);
    }

    public BooleanProperty getPenaltyBox(int index){
        return penalties.get(index);
    }

    public IntegerProperty getScore(String score){
        return scores.get(score);
    }

    public IntegerProperty getTotalScore(){
        return totalScore;
    }

    
}