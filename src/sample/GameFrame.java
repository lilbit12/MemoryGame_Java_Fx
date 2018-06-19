package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;

public class GameFrame {

    private Integer gridSize;
    private GridPane gridPane;
    protected Label timerLabel = new Label();
    public Integer seconds = 0;
    public Integer minutes = 0;
    private Timeline timeline;


    public GameFrame(Integer gridSize) {
        Stage newGameFrame = new Stage();

        BorderPane mainPane = new BorderPane();

        this.gridSize = gridSize;

        this.gridPane = new GridPane();
        gridPane.setBackground(Background.EMPTY);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);


        timerLabel.setText(seconds.toString());
        timerLabel.setTextFill(Color.GREEN);
        timerLabel.setStyle("-fx-font-size: 3em;-fx-font-family: 'sans-serif';");

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            seconds++;
                            timerLabel.setText(minutes.toString()+":"+seconds.toString());
                            if (seconds>59){
                                minutes++;
                                seconds= 0;
                                timerLabel.setText(minutes.toString()+":"+seconds.toString());
                            }

                        })
        );
        timeline.playFromStart();

        Deck deck1 = new Deck(gridSize);
        generateGrid(deck1.getDeck());
        ObservableList observableList = FXCollections.observableList(deck1.getDeck());
        observableList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                while (change.next()){
                    change.wasUpdated();
                    change.wasPermutated();
                    System.out.println("Change!");
                }

            }
        });

        mainPane.setCenter(gridPane);
        mainPane.setBottom(timerLabel);
        mainPane.setBackground(Background.EMPTY);
        BorderPane.setAlignment(timerLabel,Pos.BOTTOM_CENTER);



        Scene scene = new Scene(mainPane, 640, 480, Color.rgb(48, 194, 228));
        newGameFrame.setTitle("New Game");
        newGameFrame.setScene(scene);
        newGameFrame.show();


        }



    public void generateGrid(List<Card> deck){
        int licznik = 0;
        Card tmp;

        for (int row = 0; row < gridSize ; row++) {
            for (int column = 0; column < gridSize ; column++) {
                tmp = deck.get(licznik);
                gridPane.getChildren().add(tmp);
                GridPane.setConstraints(tmp,row,column);
                licznik++;
            }
        }
    }
}
