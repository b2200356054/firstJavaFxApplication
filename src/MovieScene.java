import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;



public class MovieScene {
    static ChoiceBox<String> hallChoiceBox; private static String chosenHall;
    public static Scene createScene(Stage theWindow){
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        String thePath = Data.getTheMovies().get(MovieChoosingScene.getMovieName()).getTrailerPath();
        Media theMedia = new Media(new File(String.format("assets\\trailers\\%s", thePath)).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(theMedia);
        mediaPlayer.setAutoPlay(true);
        MediaView theView = new MediaView(mediaPlayer);
        theView.setFitHeight(315); theView.setFitWidth(560);
        borderPane.setCenter(theView);

        Movies chosenMovie = Data.getTheMovies().get(MovieChoosingScene.getMovieName());




        HBox message = new HBox(10);
        message.setAlignment(Pos.BOTTOM_CENTER);
        Label welcomeMessage = new Label(String.format("%s (%d minutes)", chosenMovie.getName(), chosenMovie.getLength()));
        message.getChildren().add(welcomeMessage);
        borderPane.setTop(message);



        VBox theButtons = new VBox(10);
        theButtons.setPadding(new Insets(10));
        theButtons.setAlignment(Pos.CENTER);
        Button stopButton = new Button("||"); stopButton.setAlignment(Pos.TOP_CENTER);
        stopButton.setPrefWidth(40);
        Button playButton = new Button(">"); playButton.setAlignment(Pos.TOP_CENTER);
        playButton.setPrefWidth(40);
        stopButton.setOnAction(event -> {
            mediaPlayer.pause();
            theButtons.getChildren().remove(0);
            theButtons.getChildren().add(0, playButton);
        });
        playButton.setOnAction(event -> {
            mediaPlayer.play();
            theButtons.getChildren().remove(0);
            theButtons.getChildren().add(0, stopButton);
        });
        Button secBack = new Button("<<");
        stopButton.setAlignment(Pos.TOP_CENTER);
        secBack.setPrefWidth(40);
        secBack.setOnAction(event -> {
            Duration duration = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(new Duration(duration.toMillis()-10000));
        });
        Button secForward = new Button(">>");
        stopButton.setAlignment(Pos.TOP_CENTER);
        secForward.setOnAction(event -> {
            Duration duration = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(new Duration(duration.toMillis()+10000));
        });
        secForward.setPrefWidth(40);
        Button toBeginning = new Button("|<<"); stopButton.setAlignment(Pos.TOP_CENTER);
        toBeginning.setPrefWidth(40);
        toBeginning.setOnAction(event -> {
            mediaPlayer.stop();
            mediaPlayer.play();
        });
        Slider theVoiceSlider = new Slider();
        theVoiceSlider.setOrientation(Orientation.VERTICAL);
        theVoiceSlider.setValue(100);
        theVoiceSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume((Double) newValue / 100);}));
        theButtons.getChildren().addAll(stopButton, secBack,secForward, toBeginning, theVoiceSlider);
        borderPane.setRight(theButtons);

        HBox theBottomButtons = new HBox(10);
        Button backButton = new Button("< BACK");
        backButton.setOnAction(event -> {
            mediaPlayer.stop();
            theWindow.setScene(MovieChoosingScene.createScene(theWindow));});
        Button addHall = new Button("ADD HALL");
        addHall.setOnAction(event -> {
            mediaPlayer.stop();
            theWindow.setScene(AddHallScene.createScene(theWindow));});
        Button removeHall = new Button("REMOVE HALL");
        removeHall.setOnAction(event -> {
            mediaPlayer.stop();
            theWindow.setScene(RemoveHallScene.createScene(theWindow));
        });
        hallChoiceBox = new ChoiceBox<>();
        Data.fillChoiceBoxHalls(chosenMovie.getName(), hallChoiceBox);
        try {
            hallChoiceBox.setValue(hallChoiceBox.getItems().get(0));
        } catch (IndexOutOfBoundsException ignored){}
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try{
                chosenHall = hallChoiceBox.getValue();
                mediaPlayer.stop();
                theWindow.setScene(SeatScene.createScene(theWindow));
            } catch (NullPointerException ignored){}
        });
        theBottomButtons.setPadding(new Insets(10));
        theBottomButtons.setAlignment(Pos.CENTER);
        theBottomButtons.getChildren().addAll(backButton,addHall,removeHall, hallChoiceBox, okButton);

        if (Data.getUsers().get(LogInScene.getUsername()).isAdmin()) {
            borderPane.setBottom(theBottomButtons);
        } else {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(backButton, okButton, hallChoiceBox);
            borderPane.setBottom(hBox);
        }

        return new Scene(borderPane, 800, 400);
    }
    public static ChoiceBox<String> getHallChoiceBox(){
        return hallChoiceBox;
    }
    public static String getChosenHall(){
        return chosenHall;
    }
}
