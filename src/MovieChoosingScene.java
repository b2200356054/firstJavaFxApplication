import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class MovieChoosingScene {

    public static String getMovieName() {
        return movieName;
    }
    private static String movieName;
    public static Scene createScene(Stage theWindow){
        String username = LogInScene.getUsername();
        String memberships;
        boolean[] qualities = {Data.getUsers().get(username).isAdmin(), Data.getUsers().get(username).isClubMember()};
        VBox mainVBox = new VBox(5);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setPadding(new Insets(10));
        if (qualities[0] == qualities[1] && qualities[0]) {
            memberships = " (Admin - Club Member)";
        } else if (qualities[0] && !qualities[1]) {
            memberships = " (Admin)";
        } else if (!qualities[0] && qualities[1]) {
            memberships = " (Club Member)";
        } else {
            memberships = "";
        }
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        Label welcomeMessage1 = new Label(String.format("Welcome %s%s!", LogInScene.getUsername(), memberships));
        Label welcomeMessage2 = new Label("You can either select film below or do edits.");
        mainVBox.getChildren().addAll(welcomeMessage1, welcomeMessage2);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        GridPane.setConstraints(choiceBox, 0, 2);
        GridPane.setHalignment(choiceBox, HPos.CENTER);
        try {
            choiceBox.setValue(Data.getTheMoviesEntries().get(0).getKey());
            GridPane.setHalignment(choiceBox, HPos.CENTER);
            Data.fillChoiceBoxMovies(choiceBox);
        } ///////DELETE AFTERWARDS
        catch (IndexOutOfBoundsException ignored) {
        }
        choiceBox.setPrefWidth(280);


        Button okButton = new Button("OK");
        GridPane.setConstraints(okButton, 1, 2);
        okButton.setMinWidth(20);
        GridPane.setHalignment(okButton, HPos.CENTER);
        okButton.setOnAction(event -> {
            try {
                movieName = choiceBox.getValue();
                theWindow.setScene(MovieScene.createScene(theWindow));
            } catch (Exception ignored) {
            }
        });





        Button logOutButton = new Button("LOG OUT");
        GridPane.setConstraints(logOutButton, 0, 4);
        GridPane.setHalignment(logOutButton, HPos.RIGHT);
        gridPane.getChildren().add(logOutButton);
        logOutButton.setOnAction(event -> theWindow.setScene(LogInScene.createScene(theWindow)));

        if (qualities[0]) {
            Button addFilm = new Button("Add Film");
            addFilm.setOnAction(event -> theWindow.setScene(AddFilmScene.createScene(theWindow)));
            Button removeFilm = new Button("Remove Film");
            removeFilm.setOnAction(event -> theWindow.setScene(RemoveMovieScene.createScene(theWindow)));
            Button editUsers = new Button("Edit Users");
            editUsers.setOnAction(event -> theWindow.setScene(EditUsersScene.createScene(theWindow)));
            HBox randomBox = new HBox(10);
            GridPane.setConstraints(randomBox, 0, 3);
            randomBox.setAlignment(Pos.CENTER);
            randomBox.getChildren().addAll(addFilm, removeFilm, editUsers);
            gridPane.getChildren().add(randomBox);

        }

        gridPane.getChildren().addAll(choiceBox, okButton);
        mainVBox.getChildren().add(gridPane);
        return new Scene(mainVBox, 400, 250);
    }
}
