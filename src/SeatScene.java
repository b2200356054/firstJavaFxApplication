
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;


public class SeatScene {

    static String movieName = MovieChoosingScene.getMovieName(); static BorderPane mainVBox;
    static VBox centerVBox;
    static GridPane errorGrid = new GridPane();
    static GridPane gridPane = new GridPane();
    static ChoiceBox<String> userChoice; static Seat theSeat; static Halls theHall = Data.getTheMovies().get(movieName).getHalls().get(MovieScene.getChosenHall());
    static Label bought;

    static HashMap<String, Button> buttonHashMap = new HashMap<>();
    public static Scene createScene(Stage theWindow){
        mainVBox = new BorderPane();
        mainVBox.setPadding(new Insets(30));
        createButtons();
        Movies movieObject = Data.getTheMovies().get(movieName);


        HBox hBox = new HBox(10); hBox.setPadding(new Insets(10));
        Label welcomeMessage = new Label(String.format("%s (%d minutes) Hall: %s", movieName, movieObject.getLength(), MovieScene.getChosenHall()));
        hBox.getChildren().add(welcomeMessage);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        mainVBox.setTop(hBox);


        centerVBox = new VBox(10); centerVBox.setPadding(new Insets(10));
        gridPane = fillGridPane(Data.getUsers().get(LogInScene.getUsername()).isAdmin());

        userChoice = new ChoiceBox<>();
        Data.fillChoiceBoxUsers(userChoice);
        try{
            userChoice.setValue(userChoice.getItems().get(0));
        } catch (Exception ignored){}

        errorGrid.setAlignment(Pos.CENTER);
        centerVBox.getChildren().add(gridPane);
        if (Data.getUsers().get(LogInScene.getUsername()).isAdmin()){
            centerVBox.getChildren().add(userChoice);
        }
        centerVBox.getChildren().add(errorGrid);
        mainVBox.setCenter(centerVBox); centerVBox.setAlignment(Pos.TOP_CENTER);

        Button backButton = new Button("◀ BACK");
        HBox hbox1 = new HBox(10); hBox.setPadding(new Insets(10));
        hbox1.getChildren().add(backButton); hbox1.setAlignment(Pos.BOTTOM_CENTER);
        mainVBox.setBottom(hbox1);
        backButton.setAlignment(Pos.CENTER);
        backButton.setOnAction(event -> theWindow.setScene(MovieScene.createScene(theWindow)));

        return new Scene(mainVBox, 800 , 800);
    }
    public static GridPane fillGridPane(boolean admin){
        GridPane gridPane1 = new GridPane();
        gridPane1.setAlignment(Pos.CENTER);
        gridPane1.setPadding(new Insets(10));
        gridPane1.setVgap(10); gridPane1.setHgap(10);
        theHall = Data.getTheMovies().get(movieName).getHalls().get(MovieScene.getHallChoiceBox().getValue());
        for (Map.Entry<String, Button> i: buttonHashMap.entrySet()){
            if(!(Data.getUsers().get(LogInScene.getUsername()).isAdmin())){
                String owner = theHall.getSeats().get(i.getKey()).getOwner();
                try {
                    if (!((owner == null))) {
                        if(!(owner.equals(LogInScene.getUsername()))){
                            i.getValue().setDisable(true);
                        }
                    }
                } catch (NullPointerException ignored){}
            }
            gridPane1.getChildren().add(i.getValue());
        }
        return gridPane1;
    }
    public static void createButtons(){

        int row = theHall.getRow();
        int column = theHall.getColumn();
        for (int i = 0; i<row; i++){
            for (int j = 0; j < column; j++){
                String rowNum = String.valueOf(i);
                String colNum = String.valueOf(j);
                theSeat = theHall.getSeats().get(rowNum+" "+colNum);
                Button theButton = new Button(rowNum+" "+colNum);
                String seatImagePath = theSeat.getImagePath();
                Image theImage = new Image(String.format("file:%s", seatImagePath));
                ImageView imageView = new ImageView(theImage);
                imageView.setFitHeight(40); imageView.setFitWidth(40);
                theButton.setGraphic(imageView);
                theButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                theButton.setOnAction(event -> {buttonAction(theButton.getText());});
                theButton.setOnMouseEntered( event -> mouseDragAction(theButton.getText()));
                theButton.setOnMouseExited(event -> mouseDragOutAction());
                GridPane.setConstraints(theButton, j, i);
                buttonHashMap.put(rowNum+" "+colNum, theButton);
            }
        }

    }



    public static void buttonAction(String coordinates){
        Seat theSeat = Data.getTheMovies().get(movieName).getHalls().get(theHall.getHallName()).getSeats().get(coordinates);
        Label boughtMessage; String ticketName;
        String[] coordinat = coordinates.split(" ");
        if (Data.getUsers().get(LogInScene.getUsername()).isAdmin()) {
            ticketName = userChoice.getValue();
        } else {
            ticketName = LogInScene.getUsername();
        }
        if (theSeat.getOwner() == null) {
            if (Data.getUsers().get(ticketName).isClubMember()) {
                theSeat.setBoughtPrice((theHall.getPricePerSeat() * (100 - Data.getDiscount())) / 100);
            } else {
                theSeat.setBoughtPrice(theHall.getPricePerSeat());
            }
            theSeat.setOwner(ticketName);
            boughtMessage = new Label(String.format("Seat at %s-%s is bought for %s for %d TL successfully!", coordinat[0], coordinat[1], ticketName, theSeat.getBoughtPrice()));
        } else {
            theSeat.setOwner(null);
            theSeat.setBoughtPrice(theHall.getPricePerSeat());
            boughtMessage = new Label(String.format("Seat at %s-%s is refunded successfully!", coordinat[0], coordinat[1]));
        }
        GridPane.setConstraints(boughtMessage, 0, 1);
        GridPane.setHalignment(boughtMessage, HPos.CENTER);
        ImageView forImage = new ImageView(String.format("file:%s", theSeat.getImagePath()));
        forImage.setFitWidth(40); forImage.setFitHeight(40);
        buttonHashMap.get(coordinates).setGraphic(forImage);
        gridPane = fillGridPane(Data.getUsers().get(LogInScene.getUsername()).isAdmin());
        centerVBox.getChildren().remove(0);
        centerVBox.getChildren().add(0, gridPane);
        try {
            errorGrid.getChildren().remove(0);
        } catch (Exception ignored) {
        }
        errorGrid.getChildren().add(boughtMessage);
    }

    public static void mouseDragAction(String coordinates){
        Seat chosenSeat = Data.getTheMovies().get(movieName).getHalls().get(theHall.getHallName()).getSeats().get(coordinates);
        if (chosenSeat.getOwner() == null){
            bought = new Label("Not bought yet!");

        } else {
            bought = new Label(String.format("Bought by %s for %d TL!", chosenSeat.getOwner(), chosenSeat.getBoughtPrice()));
        }
        GridPane.setConstraints(bought, 0, 0);
        GridPane.setHalignment(bought, HPos.CENTER);
        errorGrid.getChildren().add(bought);
    }
    public static void mouseDragOutAction(){
        errorGrid.getChildren().remove(bought);
    }
}
