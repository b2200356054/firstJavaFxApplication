
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogInScene {
    private static String username;
    public static String getUsername() {
        return username;
    }

    static Button logIn; static Timeline timeline;
    public static void setUsername(String username) {
        LogInScene.username = username;
    }

    public static Scene createScene(Stage theWindow) {
        VBox mainVBox = new VBox(10);
        mainVBox.setAlignment(Pos.TOP_CENTER);
        mainVBox.setPadding(new Insets(20));
        Label welcomeMessage1 = new Label("Welcome to the HUCS Cinema Reservation System!");
        Label welcomeMessage2 = new Label("Please enter your credentials below and click LOGIN.");
        Label welcomeMessage3 = new Label("You can create a new account by clicking SIGN UP button.");
        Label usernameText = new Label("Username:");
        Label passwordText = new Label("Password:");
        ////////////////////////////
        GridPane gridForLogIn = new GridPane();
        gridForLogIn.setHgap(10);
        gridForLogIn.setVgap(10);
        gridForLogIn.setPadding(new Insets(10));
        gridForLogIn.setAlignment(Pos.TOP_CENTER);

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("username...");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password...");


        Button signUp = new Button("SIGN UP");
        signUp.setOnAction(event -> theWindow.setScene(SignUpScene.createScene(theWindow)));

        logIn = new Button("LOG IN");
        GridPane.setHalignment(logIn, HPos.RIGHT);
        logIn.setOnAction(event -> { ///LOGIN BUTTON ACTIONS

            if (Data.getUsers().get(usernameInput.getText()).getPassword().equals(PasswordHash.hashPassword(passwordInput.getText())) && Data.getUsers().containsKey(usernameInput.getText())) {
                setUsername(usernameInput.getText());
                theWindow.setScene(MovieChoosingScene.createScene(theWindow));
            } else {
                Data.playErrorSound();
                Label error;
                if (Data.getMaximumError() == Data.getErrorLimit()-1) {
                    logIn.setDisable(true);
                    error = new Label(String.format("ERROR: You must wait %d seconds before any other attempt!", Data.getBlockTime()));
                    try{
                        mainVBox.getChildren().remove(4);
                    } catch (Exception ignored){}
                    disableButton();
                    Data.setMaximumError(0);
                } else {
                    try{
                        mainVBox.getChildren().remove(4);
                    } catch (Exception ignored){}
                    error = new Label("ERROR: There's no such credential!");
                    Data.setMaximumError(Data.getMaximumError() + 1);
                    event.consume();
                }

                mainVBox.getChildren().add(error);
                error.setAlignment(Pos.CENTER);

            }
        });
        GridPane.setConstraints(signUp, 0, 2);
        GridPane.setConstraints(logIn, 1, 2);

        GridPane.setConstraints(usernameText, 0, 0);
        GridPane.setConstraints(usernameInput, 1, 0);
        GridPane.setConstraints(passwordText, 0, 1);
        GridPane.setConstraints(passwordInput, 1, 1);
        gridForLogIn.getChildren().addAll(usernameText, usernameInput, passwordText, passwordInput, signUp, logIn);

        //////////////////////////

        mainVBox.getChildren().addAll(welcomeMessage1, welcomeMessage2, welcomeMessage3, gridForLogIn);
        return new Scene(mainVBox, 400, 250);

    }

    private static void disableButton() {
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(Data.getBlockTime()), event->{ logIn.setDisable(false);}
        ));
        timeline.setCycleCount(1); timeline.play();
    }


}
