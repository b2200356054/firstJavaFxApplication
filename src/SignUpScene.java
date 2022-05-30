import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class SignUpScene {
    static TextField usernameInput;
    static PasswordField passwordField;
    public static Scene createScene(Stage theWindow) {
        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(10));
        mainVBox.setAlignment(Pos.TOP_CENTER);

        Label welcomeMessage = new Label("Type your credentials to sign up!");


        Label usernameText = new Label("Username:");
        Label passwordText = new Label("Password:");
        GridPane.setConstraints(usernameText, 0, 0);
        GridPane.setConstraints(passwordText, 0 , 1);

        usernameInput = new TextField();
        usernameInput.setPromptText("type a username...");
        GridPane.setConstraints(usernameInput, 1, 0);


        passwordField = new PasswordField();
        passwordField.setPromptText("type a password...");
        GridPane.setConstraints(passwordField, 1,1);


        Button backButton = new Button("< BACK");
        backButton.setOnAction(event -> theWindow.setScene(LogInScene.createScene(theWindow)));
        Button okButton = new Button("CREATE");
        okButton.setOnAction(event -> {
            createUser();
            passwordField.clear();
            usernameInput.clear();
        });
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(backButton, okButton);
        hBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10); gridPane.setVgap(10);
        gridPane.getChildren().addAll(usernameText, usernameInput, passwordText, passwordField);
        gridPane.setAlignment(Pos.CENTER);


        mainVBox.getChildren().addAll(welcomeMessage, gridPane, hBox);
        return new Scene(mainVBox,400 ,250);
    }
    public static void createUser(){
        String username = usernameInput.getText();
        String password = PasswordHash.hashPassword(passwordField.getText());
        Data.addUser(username, password, "false", "false");
    }
}
