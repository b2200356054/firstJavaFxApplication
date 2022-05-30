import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
public class Main extends Application {
    Stage theWindow;




    public static void main(String[] args){
        launch();
    }
    @Override
    public void start(Stage primaryStage) {
        Data.getData();
        theWindow = new Stage();
        theWindow.setTitle("HUCS Cinema Reservation System");
        theWindow.setScene(LogInScene.createScene(theWindow));
        Image icon = new Image("file:assets\\icons\\logo.png");
        theWindow.getIcons().add(icon);
        theWindow.show();
        theWindow.setOnCloseRequest(event -> {
            event.consume();
            try {
                Data.saveData();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }
}
