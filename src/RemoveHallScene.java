import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveHallScene {
    public static Scene createScene(Stage theWindow){
        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(10));
        mainVBox.setAlignment(Pos.CENTER);

        String movieName = MovieChoosingScene.getMovieName();

        Label welcomeMessage = new Label(String.format("Select the hall that you desire to remove from %s and then click OK.", MovieChoosingScene.getMovieName()));

        ChoiceBox<String> hallChoiceBox = new ChoiceBox<>();
        hallChoiceBox.setPrefWidth(200);
        Data.fillChoiceBoxHalls(MovieChoosingScene.getMovieName(), hallChoiceBox);
        try{
            hallChoiceBox.setValue(hallChoiceBox.getItems().get(0));
        } catch (IndexOutOfBoundsException ignored){}

        HBox hBox = new HBox(10);
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            if (!hallChoiceBox.getValue().equals("")) {
                Data.getTheMovies().get(movieName).removeHall(hallChoiceBox.getValue());
                hallChoiceBox.getItems().clear();
                Data.fillChoiceBoxHalls(movieName, hallChoiceBox);
            }
        });
        Button backButton = new Button("◀ BACK");
        backButton.setOnAction(event -> theWindow.setScene(MovieScene.createScene(theWindow)));
        hBox.getChildren().addAll(backButton, okButton); hBox.setAlignment(Pos.CENTER);

        mainVBox.getChildren().addAll(welcomeMessage,hallChoiceBox,hBox);

        return new Scene(mainVBox, 400,250);
    }
}
