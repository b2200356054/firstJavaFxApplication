import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AddFilmScene {
    public static Scene createScene(Stage theWindow){
        BorderPane mainVBox = new BorderPane();
        mainVBox.setPadding(new Insets(10));

        VBox bottomVBox = new VBox(10);

        Label welcomeMessage = new Label("Please give name, relative path of the trailer and duration of the film.");

        Label name = new Label("Name");
        GridPane.setConstraints(name, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);
        Label trailerPath = new Label("Trailer (Path)");
        GridPane.setConstraints(trailerPath, 0, 1);
        TextField trailerPathInput = new TextField();
        GridPane.setConstraints(trailerPathInput, 1, 1);
        Label duration = new Label("Duration (m)");
        GridPane.setConstraints(duration, 0, 2);
        TextField durationInput = new TextField();
        GridPane.setConstraints(durationInput, 1, 2);

        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,10,10,10));
        gridPane.setHgap(10); gridPane.setVgap(10);
        gridPane.getChildren().addAll(name, nameInput, trailerPath, trailerPathInput, duration, durationInput);



        HBox hBox = new HBox(10); hBox.setAlignment(Pos.CENTER);


        Button backButton = new Button("< BACK");
        backButton.setAlignment(Pos.CENTER_LEFT);
        Button okButton = new Button("OK");
        okButton.setAlignment(Pos.CENTER_RIGHT);
        backButton.setOnAction(event -> theWindow.setScene(MovieChoosingScene.createScene(theWindow)));
        okButton.setOnAction(event -> {
            int continued = 0;
            String movieName = null; String trailerPath1; int duration1 = 0;
            try {
                movieName = nameInput.getText();
                if (!movieName.equals("")) {
                    try {
                        trailerPath1 = String.format("assets\\\\\\trailers\\\\\\%s",trailerPathInput.getText());
                        if (!trailerPath1.equals("")) {
                            try{
                                File theFile = new File(trailerPath1);
                                Scanner scanner = new Scanner(theFile);
                                try {
                                    if (!durationInput.getText().equals(""))
                                        try{
                                            duration1 = Integer.parseInt(durationInput.getText());
                                        } catch (NumberFormatException e){
                                            continued = 5;
                                    }
                                    else {
                                        continued = 4;
                                    }
                                } catch (Exception e) {
                                    continued = 4;
                                }
                            } catch (FileNotFoundException e){
                                continued = 2;
                            }
                        } else {
                            continued = 3;
                        }
                    } catch (Exception e) {
                        continued = 3;
                    }
                } else {
                    continued = 1;
                }
            } catch(Exception ignored){}

            if (continued == 0) {
                Data.addFilm(movieName, trailerPathInput.getText(), duration1);
                nameInput.clear();
                trailerPathInput.clear();
                durationInput.clear();
            } else {
                Label errorMessage = null;
                Data.playErrorSound();
                switch (continued){
                    case 1:
                        errorMessage = new Label("ERROR: Film name can not be empty!");
                        break;
                    case 2:
                        errorMessage = new Label("ERROR: No such trailer file!");
                        break;
                    case 3:
                        errorMessage = new Label("ERROR: Trailer path can not be empty!");
                        break;
                    case 4:
                        errorMessage = new Label("ERROR: Duration can not be empty!");
                        break;
                    case 5:
                        errorMessage = new Label("ERROR: Duration has to be positive integer!");
                        break;
                }
                try{
                    bottomVBox.getChildren().remove(1);
                } catch (Exception ignored){}

                errorMessage.setAlignment(Pos.CENTER);
                bottomVBox.getChildren().add(errorMessage);

            }

        });

        hBox.getChildren().addAll(backButton, okButton);
        bottomVBox.getChildren().add(hBox);


        welcomeMessage.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.setAlignment(Pos.TOP_CENTER);
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);


        mainVBox.setTop(welcomeMessage);
        mainVBox.setCenter(gridPane);
        mainVBox.setBottom(bottomVBox);


        return new Scene(mainVBox, 400, 250);
    }
}
