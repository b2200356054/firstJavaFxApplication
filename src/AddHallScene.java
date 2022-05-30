import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class AddHallScene {
    public static Scene createScene(Stage theWindow){
        VBox mainVBox = new VBox(10);
        mainVBox.setAlignment(Pos.CENTER);
        String movieChoosen = MovieChoosingScene.getMovieName();

        Label movieName = new Label(String.format("%s (%d minutes)", movieChoosen, Data.getTheMovies().get(movieChoosen).getLength()));
        mainVBox.getChildren().add(movieName);

        Label row = new Label("Row:");
        GridPane.setConstraints(row, 0,0);
        ChoiceBox<Integer> rowInput = new ChoiceBox<>();
        rowInput.setValue(3);
        GridPane.setHalignment(rowInput, HPos.CENTER);
        GridPane.setConstraints(rowInput, 1,0);

        Label column = new Label("Column:");
        GridPane.setConstraints(column, 0,1);
        ChoiceBox<Integer> columnInput = new ChoiceBox<>();
        columnInput.setValue(3);
        GridPane.setHalignment(columnInput, HPos.CENTER);
        GridPane.setConstraints(columnInput, 1 ,1);

        for (int i = 3; i<= 10; i++){
            rowInput.getItems().add(i);
            columnInput.getItems().add(i);
        }

        Label name = new Label("Name:");
        GridPane.setConstraints(name, 0, 2);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 2);
        nameInput.setPromptText("Enter a movie name...");

        Label price = new Label("Price:");
        GridPane.setConstraints(price, 0, 3);
        TextField priceInput = new TextField();
        GridPane.setConstraints(priceInput, 1, 3);
        priceInput.setPromptText("Type a price...");

        Button backButton = new Button("◀ BACK");
        GridPane.setConstraints(backButton, 0, 4);
        backButton.setOnAction(event -> theWindow.setScene(MovieScene.createScene(theWindow)));
        Button okButton = new Button("OK");
        GridPane.setHalignment(okButton, HPos.RIGHT);
        GridPane.setConstraints(okButton, 1,4);


        okButton.setOnAction(event -> {
            Label errorMessage = null;
            if (!nameInput.getText().equals("")){
                if (!priceInput.getText().equals("")){
                    try{
                        int price1 = Integer.parseInt(priceInput.getText());
                        Movies.addHall(movieChoosen, nameInput.getText(), price1, rowInput.getValue(), columnInput.getValue());
                        nameInput.clear();
                        priceInput.clear();
                        Data.fillChoiceBoxHalls(movieChoosen, MovieScene.getHallChoiceBox());
                    } catch (NumberFormatException e){
                        errorMessage = new Label("Price should be a positive integer!");
                    }
                } else {
                    errorMessage = new Label("Price can not be empty!");
                }
            } else {
                errorMessage = new Label("Name can not be empty!");
            }
            try {
                mainVBox.getChildren().remove(2);
            } catch (Exception ignored){}
            if (errorMessage != null) {
                errorMessage.setAlignment(Pos.CENTER);
                mainVBox.getChildren().add(errorMessage);
                Data.playErrorSound();
            }

        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10); gridPane.setHgap(10);
        gridPane.getChildren().addAll(row, rowInput, column, columnInput, name, nameInput, price, priceInput, backButton, okButton);
        mainVBox.getChildren().add(gridPane);

        return new Scene(mainVBox,400 ,270);
    }
}
