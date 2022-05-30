import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class RemoveMovieScene {
    public static Scene createScene(Stage theWindow){
        VBox mainVBox = new VBox(10); mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(10));

        Label welcomeMessage = new Label("Select the film that you desire to remove and then click OK");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        try{
            Data.fillChoiceBoxMovies(choiceBox);
            choiceBox.setValue(Data.getTheMoviesEntries().get(0).getKey());}
        catch (Exception ignored){}
        choiceBox.setPrefWidth(280);


        Button backButton = new Button("◀ BACK");
        backButton.setOnAction(event -> theWindow.setScene(MovieChoosingScene.createScene(theWindow)));

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            Data.removeFilm(choiceBox.getValue());
            choiceBox.getItems().clear();
            Data.fillChoiceBoxMovies(choiceBox);
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(backButton, okButton); hBox.setAlignment(Pos.CENTER);

        mainVBox.getChildren().addAll(welcomeMessage, choiceBox, hBox);

        return new Scene(mainVBox, 400, 250);
    }
}
